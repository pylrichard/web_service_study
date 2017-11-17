package com.bd.roncoo.book.shop.admin.controller;

import com.bd.roncoo.book.shop.dto.BookCondition;
import com.bd.roncoo.book.shop.dto.BookInfo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    /**
     * 将查询参数封装在BookCondition，分页查询请求由前端传入
     * 将请求参数封装成Pageable对象，传递给Spring Data JPA的Repository分页查询方法即可
     */
    @GetMapping
    @JsonView(BookInfo.BookListView.class)
    public List<BookInfo> query(BookCondition condition, @PageableDefault(size = 10)Pageable pageable) {
        //当前是第几页，0表示第1页
        System.out.println(pageable.getPageNumber());
        //每页有多少条记录
        System.out.println(pageable.getPageSize());
        //排序规则
        System.out.println(pageable.getSort());

        List<BookInfo> bookInfos = new ArrayList<>();
        bookInfos.add(new BookInfo());
        bookInfos.add(new BookInfo());
        bookInfos.add(new BookInfo());

        return bookInfos;
    }

    //:\\d正则表达式，希望传入的id为1位数
    @GetMapping("/{id:\\d}")
    //BookInfo.content在调用getInfo()时才显示
    @JsonView(BookInfo.BookDetailView.class)
    public BookInfo getInfo(@PathVariable long id) throws Exception {
        /*
            首先被ExceptionHandlerController.handleRuntimeException()处理，不会传递到TimeInterceptor.afterCompletion
            throw new RuntimeException("getInfo exception")
            抛出检查异常，被TimeInterceptor.afterCompletion处理
        */
        throw new Exception("getInfo exception");
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
