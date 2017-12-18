package com.bd.roncoo.book.shop.db.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.List;

/**
 * 注解@Table(name = "bs_category")添加前缀，生成新表
 * 具体类的配置会覆盖applicaton.properties的全局配置spring.jpa.hibernate.naming.implicit-strategy，需要去掉
 * 注解@Entity表示将Category映射为数据库中的一张表
 * 在application.properties中添加spring.jpa.generate-ddl=true才会在数据库中生成对应的表
 */
@Entity
@Getter
@Setter
public class Category extends DomainImpl {
    /**
     * 没有添加注解，默认添加@Basic
     * String在表中转换为VARCHAR
     */
    @Basic
    //@Column(name = "bs_name", length = 10, nullable = false(非空), unique = true(唯一))
    @Column(length = 10, nullable = false, unique = true)
    private String name;

    /**
     * 存储计算得到的中间值，不需要在表中生成对应的字段
     */
    @Transient
    private String tmp;

    /**
     * 生成bs_bs_category_bs_book表实现单向一对多关系，推荐建立双向关系，方便通过get()获取数据
     * mappedBy表示由Book对象的category变量来实现双向关系，Category对象不管理
     * orphanRemoval表示集合中元素被移除后是否从DB删除
     *  比如books有5本书，执行books.remove(1)移除1本书，这本书不属于任何一个Category，是否从DB删除相应的Book记录
     *  true表示删除，默认为false
     * cascade表示级联操作，默认为空，不进行删除
     *  比如cascade = CascadeType.REMOVE表示删除Category对象记录时会删除相应的Book对象记录
     *  有外键时删除会报错
     */
    @OneToMany(mappedBy = "category")
    private List<Book> books;
}
