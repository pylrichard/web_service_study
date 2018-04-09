package com.bd.roncoo.book.shop.book.service.impl;

import com.bd.roncoo.book.shop.book.service.BaseTest;
import com.bd.roncoo.book.shop.common.dto.BookInfo;
import com.bd.roncoo.book.shop.common.service.BookService;
import com.bd.roncoo.book.shop.db.repository.BookRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookServiceImplTest extends BaseTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Test
    public void whenCreateSuccess() {
        long beforeCount = bookRepository.count();
        BookInfo info = new BookInfo();
        info.setName("天龙八部");
        info = bookService.create(info);
        long afterCount = bookRepository.count();
        Assert.assertEquals(beforeCount + 1, afterCount);
        Assert.assertEquals("天龙八部", bookRepository.findOne(info.getId()).getName());
    }

    @Test
    public void whenUpdateSuccess() {
        BookInfo info = new BookInfo();
        info.setId(1L);
        info.setName("神雕侠侣");
        info = bookService.update(info);
        Assert.assertEquals("神雕侠侣", bookRepository.findOne(info.getId()).getName());
    }
}
