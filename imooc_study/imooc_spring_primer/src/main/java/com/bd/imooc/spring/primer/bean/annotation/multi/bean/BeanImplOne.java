package com.bd.imooc.spring.primer.bean.annotation.multi.bean;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 实现此接口可以指定自身在List中的顺序，对Map无效
 */
@Order(value = 1)
@Component
public class BeanImplOne implements BeanInterface {
}
