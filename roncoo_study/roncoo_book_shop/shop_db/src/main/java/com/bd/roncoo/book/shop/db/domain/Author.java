package com.bd.roncoo.book.shop.db.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Author extends DomainImpl {
    private String name;

    /**
     * 添加@NotBlank则ORM框架会检查保证email必须有值，不能为空，但不影响数据库中字段定义，数据库中字段定义没有指定NOT NULL
     * 不同于@Column的nullable为true，则字段定义为NOT NULL
     */
    @Email
    private String email;

    /**
     * length指定VARCHAR长度
     * columnDefinition指定建表SQL语句中字段的类型限制值
     */
    @Column(columnDefinition = "INT(3)")
    private int age;

    /**
     * 生日不需要时分秒信息
     */
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(columnDefinition = "VARCHAR(2)")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    /**
     * 被注入，区别与可注入@Embeddable
     */
    @Embedded
    private Address address;

    /**
     * 集合映射，实现一对多关系，生成一个Author对象(在bs_author表插入一条记录)，添加3个hobbies(在bs_author_hobbies表插入3条记录)
     * 读取bs_author表中每一条记录时，会同时读取bs_author_hobbies表中对应的记录(多条)
     * 注解@ElementCollection不仅可以应用在String上，也可以应用在@Embeddable对象上
     */
    @ElementCollection
    private List<String> hobbies;

    @ElementCollection
    private List<Address> addresses;

    /**
     * 注解@OrderBy指定排序，List集合中元素是有序的，默认按主键升序排列
     * 注解@OneToMany由BookAuthor.author维护一对多关系
     * 指向Book就是一对多关系，这里要实现多对多关系，指向中间对象BookAuthor
     */
    @OrderBy("book.name ASC")
    @OneToMany(mappedBy = "author")
    private List<BookAuthor> books;

    /**
     * 平常使用select * from查询常用字段所在的表Author
     * 需要的时候通过一对一关系(info变量)查询不常用字段所在的表AuthorInfo
     */
    @OneToOne
    private AuthorInfo info;
}
