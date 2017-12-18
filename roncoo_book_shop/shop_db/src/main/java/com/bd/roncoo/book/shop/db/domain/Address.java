package com.bd.roncoo.book.shop.db.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * 此处不适用@Entity
 * 可注入区别于@Embedded，在多个对象嵌入此对象
 * 主对象和被引用对象生命周期一致才可以进行嵌入，比如Author和Book的生命周期不一致，Author不在人世，但Book还在
 * 生命周期不一致的对象之间使用一对多/多对一的关系
*/
@Embeddable
@Getter
@Setter
public class Address {
    private String province;

    private String city;

    private String area;

    private String address;

    private String zipCode;
}
