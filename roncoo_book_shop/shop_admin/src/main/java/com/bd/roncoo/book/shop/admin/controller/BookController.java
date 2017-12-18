package com.bd.roncoo.book.shop.admin.controller;

import com.bd.roncoo.book.shop.common.dto.BookCondition;
import com.bd.roncoo.book.shop.common.dto.BookInfo;
import com.bd.roncoo.book.shop.common.service.BookService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/book")
public class BookController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ConcurrentMap<Long, DeferredResult<BookInfo>> map = new ConcurrentHashMap<>();
    @Autowired
    private BookService bookService;

    /**
     * 注解@RequestParam的required()默认是true，不传参会报错，设置了defaultValue就不会报错了
     * public List<BookInfo> query(@RequestParam(value = "name",
     *                          defaultValue = "hello spring") String bookName, long categoryId) {
     * 将查询参数封装在BookCondition，分页查询请求由前端传入
     * 将请求参数封装成Pageable对象，传递给Spring Data JPA的Repository分页查询方法即可
     */
    @GetMapping
    @JsonView(BookInfo.BookListView.class)
    @ApiOperation("查询图书信息")
    public Page<BookInfo> query(BookCondition condition, @PageableDefault(size = 10) Pageable pageable) {
        logger.info("call query");
        return bookService.query(condition, pageable);
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
    /*
        需要与AdminUserDetailsServiceImpl.loadUserByUsername中配置的权限一致
        Dubbo分布式服务下Service方法权限判断不可用，调用方法的后台服务与Session所在线程的Web服务不在同一台服务器上
        此机制只能在Web服务使用，依赖于SecurityContext，从ThreadLocal的Session读取得到SecurityContext
        一般在Web服务配置HTTP请求权限判断
    */
    @PreAuthorize("hasAuthority('admin')")
    public BookInfo getInfo(@ApiParam("图书id") @PathVariable Long id) throws Exception {
        return bookService.getInfo(id);
    }

    /**
     * 传入的参数是json格式的字符串，保存在request的body中，需要添加@RequestBody处理
     * BookInfo.content添加@NotBlank后，Spring MVC不会进行非空检查，需要添加@Valid才会
     */
    @PostMapping
    public BookInfo create(@Valid @RequestBody BookInfo info, BindingResult result) {
        return bookService.create(info);
    }

    @PutMapping("/{id}")
    public BookInfo update(@Valid @RequestBody BookInfo info, BindingResult result) {
        return bookService.update(info);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        bookService.delete(id);
    }
}
