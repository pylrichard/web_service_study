package com.bd.java.se.study.annotation.dao;

/**
 * 将类映射到数据库表
 */
@Table("user")
public class User {
    @Column("id")
    private int id;
    @Column("user_name")
    private String userName;
    @Column("age")
    private int age;
    @Column("email")
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
