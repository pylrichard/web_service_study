package com.bd.roncoo.book.shop.db.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 注解@Table(name = "bd_category")添加前缀，生成新表，具体类的配置会覆盖全局配置，需要去掉
 * 注解@Entity表示将Category映射为数据库中的一张表
 * 在application.properties中添加spring.jpa.generate-ddl=true才会在数据库中生成对应的表
 */
@Entity
public class Category {
    /**
     * 注解@Id表示主键
     * 注解@GeneratedValue指定主键生成策略
     * GeneratedValue.strategy()默认为GenerationType.AUTO，根据数据库在TABLE/SEQUENCE/IDENTITY之间选择策略
     * MySQL就使用IDENTITY，既是Auto Increment
     * JPA会检查数据库对应的表/字段是否存在，如果存在则新设置不会生效，生效则需要在数据库中删除对应的表/字段
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 没有添加注解，默认添加@Basic
     */
    @Basic
    //@Column(name = "bd_name", length = 10, nullable = false(非空), unique = true(唯一))
    @Column(length = 10, nullable = false, unique = true)
    private String name;

    /**
     * 存储计算得到的中间值，不需要在表中生成对应的字段
     */
    @Transient
    private String tmp;

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

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }
}
