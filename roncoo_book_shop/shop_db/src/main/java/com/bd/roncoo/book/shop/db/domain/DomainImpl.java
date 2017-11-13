package com.bd.roncoo.book.shop.db.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 注解@MappedSuperclass标记的父类被子类继承，修改父类的成员，子类对应的表也随之修改
 */
@MappedSuperclass
public class DomainImpl {
    /**
     * 注解@Id表示主键
     *
     * 注解@GeneratedValue指定主键生成策略
     * GeneratedValue.strategy()默认为GenerationType.AUTO，根据数据库在TABLE/SEQUENCE/IDENTITY之间选择策略
     * MySQL就使用IDENTITY，既是Auto Increment
     * JPA会检查数据库对应的表/字段是否存在，如果存在则新设置不会生效，生效则需要在数据库中删除对应的表/字段
     * Long在表中转换为BIGINT
     */
    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
