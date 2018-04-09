package com.bd.roncoo.book.shop.db.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
/*
    SINGLE_TABLE为默认策略，父类和子类在同一张表中，通过dtype区分是哪个子类的记录
    需要删除book_shop库
    主键在bs_book表，bs_printbook和bs_ebook通过外键连接到bs_book
    @Inheritance(strategy = InheritanceType.JOINED)
    TABLE_PER_CLASS不能使用自增主键，bs_book、bs_printbook、bs_ebook都有自己的主键，主键会有冲突
*/
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//定义抓取策略
@NamedEntityGraph(name = "Book.fetch.category.and.author",
        attributeNodes = {
                @NamedAttributeNode("category"),
                @NamedAttributeNode("authors")
        }
)
@Getter
@Setter
public class Book extends DomainImpl {
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

    /**
     * 注解@ManyToMany不建议使用，多对多关系通过中间对象拆分为两个一对多关系
     * 通过中间对象BookAuthor可以方便地构建查询，比如通过book成员查询得到此book的所有author
     * 由BookAuthor.book维护一对多关系
     */
    @OneToMany(mappedBy = "book")
    private List<BookAuthor> authors;

    /**
     * 实现乐观锁
     */
    @Version
    private int version;
}
