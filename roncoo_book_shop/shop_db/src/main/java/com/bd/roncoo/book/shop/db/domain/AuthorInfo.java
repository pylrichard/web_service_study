package com.bd.roncoo.book.shop.db.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 不常用字段保存在单独的一张表中
 */
@Entity
public class AuthorInfo {
    @Id
    @GeneratedValue
    private Long Id;

    private String school;

    /**
     * 一对一关系是一对多关系的特例
     *  配置双向一对一关系，只需要一方管理关系，一般由常用对象(此处是Author，info变量)管理
     */
    @OneToOne(mappedBy = "info")
    private Author author;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
