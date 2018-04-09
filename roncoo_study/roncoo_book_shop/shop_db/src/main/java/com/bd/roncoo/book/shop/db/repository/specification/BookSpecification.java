package com.bd.roncoo.book.shop.db.repository.specification;

import com.bd.roncoo.book.shop.common.dto.BookCondition;
import com.bd.roncoo.book.shop.db.domain.Book;
import com.bd.roncoo.book.shop.db.repository.support.QueryWrapper;

public class BookSpecification extends BookShopSpecification<Book, BookCondition> {
    public BookSpecification(BookCondition condition) {
        super(condition);
    }

    @Override
    protected void addCondition(QueryWrapper<Book> queryWrapper) {
        addLikeCondition(queryWrapper, "name");
    }
}
