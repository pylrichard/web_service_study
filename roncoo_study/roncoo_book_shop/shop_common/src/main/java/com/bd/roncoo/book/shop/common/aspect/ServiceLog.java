package com.bd.roncoo.book.shop.common.aspect;

import java.lang.annotation.*;

/**
 * 注解@Target 表示注解可以声明在类、方法上
 * 注解@Retention 表示何时起作用，RUNTIME表示运行时起作用，编译时不要去除
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLog {
}
