package com.bd.roncoo.book.shop.common.service;

import com.bd.roncoo.book.shop.common.dto.BookCondition;
import com.bd.roncoo.book.shop.common.dto.BookInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookInfo getInfo(Long id);

    Page<BookInfo> query(BookCondition condition, Pageable pageable);

    BookInfo create(BookInfo info);

    BookInfo update(BookInfo info);

    void delete(long id);

    /**
     * 定时任务方法无参数无返回值
     */
    void task() throws Exception;
}
