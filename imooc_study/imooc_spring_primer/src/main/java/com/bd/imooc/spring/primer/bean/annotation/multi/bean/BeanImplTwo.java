package com.bd.imooc.spring.primer.bean.annotation.multi.bean;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value = 2)
@Component
public class BeanImplTwo implements BeanInterface {
}
