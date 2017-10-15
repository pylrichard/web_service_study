package com.imooc.aop.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Inherited
public @interface NeedSecuredClass {}
