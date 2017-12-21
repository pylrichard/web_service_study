package com.bd.imooc.spring.bean.annotation.multi.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BeanInvoker {
    /**
     * 自动注入2个BeanInterface实现类对象
     * <p>
     * 注解@Autowired由BeanPostProcessor处理，不能在BeanPostProcessor中应用@Autowired，会造成循环依赖，需要通过XML或者@Bean加载
     */
    @Autowired
    private List<BeanInterface> list;

    /**
     * String是Bean的id
     */
    @Autowired
    private Map<String, BeanInterface> map;

    @Autowired
    //指定唯一的Bean
    @Qualifier("beanImplTwo")
    private BeanInterface beanInterface;

    public void say() {
        if (list != null && list.size() != 0) {
            System.out.println("list...");
            for (BeanInterface bean : list) {
                System.out.println(bean.getClass().getName());
            }
        } else {
            System.out.println("List<BeanInterface> list is null");
        }
        System.out.println();
        if (map != null && map.size() != 0) {
            System.out.println("map...");
            for (Map.Entry<String, BeanInterface> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "      " + entry.getValue().getClass().getName());
            }
        } else {
            System.out.println("Map<String, BeanInterface> map is null");
        }
        System.out.println();
        if (beanInterface != null) {
            System.out.println(beanInterface.getClass().getName());
        } else {
            System.out.println("beanInterface is null");
        }
    }
}
