package com.bd.roncoo.book.shop.db.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 中间对象，保存多对多关系
 */
@Entity
@Getter
@Setter
public class BookAuthor {
    @Id
    @GeneratedValue
    private Long Id;

    /**
     * 一本Book有哪些Author
     */
    @ManyToOne
    private Book book;

    /**
     * 一个Author写了哪些Book
     */
    @ManyToOne
    private Author author;
}
