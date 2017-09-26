package com.bd.java_se_study.annotation;

import java.lang.annotation.*;

/**
 * 见解析Java注解
 *
 * 注解只有1个成员方法，成员名为value
 */
//作用于类和方法，见9.png
@Target({ElementType.METHOD, ElementType.TYPE})
//运行机制，见10.png
@Retention(RetentionPolicy.RUNTIME)
//允许子类继承，接口不行
@Inherited
//生成Java Doc包含注解，见12.png
@Documented
public @interface Description {
    /**
     * 方法无参无异常，返回类型受限，见8.png
     */
    String desc();

    String author() default "pyl";
}
