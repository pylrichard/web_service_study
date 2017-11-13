package com.bd.roncoo.book.shop.db.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    /**
     * 在bs_book表中生成bs_category_bs_id外键，指向bs_category的bs_id主键，实现单向多对一关系，通过Book对象访问category信息
     *
     * ManyToOne中targetEntity表示默认关联的实体类，不需要设置
     * fetch表示实体加载方式
     *  LAZY获取Book对象信息的同时不获取Category对象信息，当调用getCategory()时才获取
     *  EAGER获取Book对象信息的同时获取Category对象信息，默认设置
     * optional表示Category是否可以为空。默认为true，即Book可以不属于任何Category；为false，表示Book必须属于一个Category
     * cascade表示与此实体关联的实体的级联处理类型，不在多对一(@ManyToOne)的多端对象Book上设置
     */
    @ManyToOne
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
