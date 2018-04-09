package com.bd.roncoo.spring.boot.primer.bean;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

public class User {
    private int id;
    //见addUser的BindingResult
    @NotBlank(message = "用户名必传")
    private String name;
    @Min(value = 18, message = "未成年人不能注册用户")
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
