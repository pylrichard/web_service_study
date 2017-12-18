package com.bd.roncoo.book.shop.book.service.impl;

import com.bd.roncoo.book.shop.common.dto.BookCondition;
import com.bd.roncoo.book.shop.common.dto.BookInfo;
import com.bd.roncoo.book.shop.common.service.BookService;
import com.bd.roncoo.book.shop.db.domain.Book;
import com.bd.roncoo.book.shop.db.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("bookService")
@Transactional(rollbackFor = Exception.class)
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookInfo getInfo(Long id) {
        Book book = bookRepository.findOne(id);
        BookInfo bookInfo = new BookInfo();
        BeanUtils.copyProperties(book, bookInfo);

        return bookInfo;
    }

    @Override
    public Page<BookInfo> query(BookCondition condition, Pageable pageable) {
        return null;
    }

    @Override
    public BookInfo create(BookInfo info) {
        Book book = new Book();
        book.setName(info.getName());
        bookRepository.save(book);
        info.setId(book.getId());

        return info;
    }

    @Override
    public BookInfo update(BookInfo info) {
        Book book = bookRepository.findOne(info.getId());
        book.setName(info.getName());
        bookRepository.save(book);

        return info;
    }

    @Override
    public void delete(long id) {
    }
}
