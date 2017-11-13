package com.bd.roncoo.book.shop.db.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<BookAuthor> getBooks() {
        return books;
    }

    public void setBooks(List<BookAuthor> books) {
        this.books = books;
    }

    public AuthorInfo getInfo() {
        return info;
    }

    public void setInfo(AuthorInfo info) {
        this.info = info;
    }
}
