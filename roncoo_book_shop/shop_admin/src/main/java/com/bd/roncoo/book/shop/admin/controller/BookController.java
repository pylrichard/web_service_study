package com.bd.roncoo.book.shop.admin.controller;

import com.bd.roncoo.book.shop.dto.BookCondition;
import com.bd.roncoo.book.shop.dto.BookInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public BookInfo getInfo(@PathVariable long id) {
        System.out.println(id);

        BookInfo info = new BookInfo();
        info.setId(1);
        info.setName("战争与和平");

        return info;
    }
}
