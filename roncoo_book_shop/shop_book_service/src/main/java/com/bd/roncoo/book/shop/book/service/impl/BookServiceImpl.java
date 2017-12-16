package com.bd.roncoo.book.shop.book.service.impl;

import com.bd.roncoo.book.shop.common.dto.BookInfo;
import com.bd.roncoo.book.shop.common.service.BookService;
import org.springframework.stereotype.Service;

@Service("bookService")
public class BookServiceImpl implements BookService {
    @Override
    public BookInfo getInfo(Long id) {
        return null;
    }
}
