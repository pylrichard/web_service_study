package com.bd.roncoo.book.shop.db.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 此继承策略适用于父类与子类差异不大，父类维护大部分成员，子类成员允许为空
 */
@Entity
@Getter
@Setter
public class EBook extends Book {
    /**
     * 不能设置为NOT NULL，需要允许非空
     * PrintBook的format为空
     * EBook的printDate为空
     * 如果设置为NOT NULL，insert会失败
    */
    private String format;
}
