package com.bd.roncoo.book.shop.admin.controller;

import com.bd.roncoo.book.shop.common.dto.BookCondition;
import com.bd.roncoo.book.shop.common.dto.BookInfo;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/book")
public class BookController {
    private ConcurrentMap<Long, DeferredResult<BookInfo>> map = new ConcurrentHashMap<>();

    /**
     * 将查询参数封装在BookCondition，分页查询请求由前端传入
     * 将请求参数封装成Pageable对象，传递给Spring Data JPA的Repository分页查询方法即可
     */
    @GetMapping
    @JsonView(BookInfo.BookListView.class)
    @ApiOperation("查询图书信息")
    public List<BookInfo> query(BookCondition condition, @PageableDefault(size = 10)Pageable pageable) {
        //当前是第几页，0表示第1页
        System.out.println(pageable.getPageNumber());
        //每页有多少条记录
        System.out.println(pageable.getPageSize());
        //排序规则
        System.out.println(pageable.getSort());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        if (authentication != null) {
            //打印用户认证信息
            System.out.println(authentication.getPrincipal());
        }
        List<BookInfo> bookInfos = new ArrayList<>();
        bookInfos.add(new BookInfo());
        bookInfos.add(new BookInfo());
        bookInfos.add(new BookInfo());

        return bookInfos;
    }

    /**
     * 从Spring MVC 3.2开始，Controller中的方法可以返回Callable类型的返回值Callable<V>
     * 从Spring容器管理的线程中异步产生返回值，而Servlet容器(Tomcat)主线程会被释放，处理其它请求
     * Spring MVC从线程池中取出一个线程处理Callable，当Callable返回时，请求会被发送回Servlet容器，并处理Callable
     * 整个处理过程对前端是透明的，如果Callable处理需要1s，前端发送的请求需要等待1s才能获取到处理结果
     * 对Tomcat并不会占用一个线程1s，开始处理请求的线程在这1s中可以处理很多其它请求，提高Tomcat吞吐量
     */
    //:\\d正则表达式，希望传入的id为1位数
    @GetMapping("/{id:\\d}")
    //BookInfo.content在调用getInfo()时才显示
    @JsonView(BookInfo.BookDetailView.class)
    @ApiOperation("获取图书详细信息")
    public DeferredResult<BookInfo> getInfo(@ApiParam("图书id") @PathVariable Long id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        if (authentication != null) {
            System.out.println(authentication.getPrincipal());
        }
        long startTime = System.currentTimeMillis();
        String name = Thread.currentThread().getName();
        System.out.println("Tomcat thread " + name + " begin");

        //此线程接收请求，获取响应在另一个线程中
        DeferredResult<BookInfo> result = new DeferredResult<>();
        map.put(id, result);

        long endTime = System.currentTimeMillis() - startTime;
        //耗时4ms
        System.out.println("Tomcat thread " + name + " end, execution time = " + endTime + "ms");

        /*
            Tomcat主线程返回，去处理其它请求，等待Spring管理的线程返回结果给Tomcat主线程，Tomcat主线程再返回给前端
            前端获取响应结果需要耗时1s多，采用以上多线程模型后时间并不会缩短，但Tomcat的吞吐量提高了，可以同时处理更多请求
         */
        return result;
    }

    /**
     * 当监听到队列中有新消息时，调用此方法，返回结果给前端
     */
    @SuppressWarnings("unused")
    private void setResult(BookInfo info) {
        map.get(info.getId()).setResult(info);
    }

    /**
     * 传入的参数是json格式的字符串，保存在request的body中，需要添加@RequestBody处理
     * BookInfo.content添加@NotBlank后，Spring MVC不会进行非空检查，需要添加@Valid才会
     */
    @PostMapping
    public BookInfo create(@Valid @RequestBody BookInfo info, BindingResult result) {
        //BookInfo.content为null时提示错误，并不会中断程序的运行
        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        System.out.println("id is " + info.getId());
        System.out.println("name is " + info.getName());
        System.out.println("content is " + info.getContent());
        System.out.println("publishDate is " + info.getPublishDate());
        info.setId(1L);

        return info;
    }

    @PutMapping("/{id}")
    public BookInfo update(@Valid @RequestBody BookInfo info, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        System.out.println("id is " + info.getId());
        System.out.println("name is " + info.getName());
        System.out.println("content is " + info.getContent());
        System.out.println("publishDate is " + info.getPublishDate());

        return info;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        System.out.println(id);
    }
}
