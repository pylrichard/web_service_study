package com.bd.imooc.spring.bean.annotation.injection.service;

import com.bd.imooc.spring.bean.annotation.injection.dao.InjectionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InjectionServiceImpl implements InjectionService {
    /**
     * 和setter()上添加@Autowired类似，见36~40.png
     * <p>
     * 注解@Autowired相当于setter
     */
    @Autowired
    private InjectionDAO injectionDAO;

    /**
     * 找不到合适的bean导致autowiring失败抛出异常，通过(required = false)避免
     * <p>
     * 每个类只能有一个构造器标记为required = true
     */
    @Autowired(required = false)
    public InjectionServiceImpl(InjectionDAO injectionDAO) {
        this.injectionDAO = injectionDAO;
    }

    @Autowired
    public void setInjectionDAO(InjectionDAO injectionDAO) {
        this.injectionDAO = injectionDAO;
    }

    @Override
    public void save(String arg) {
        //模拟业务操作
        System.out.println("Service接收参数：" + arg);
        arg = arg + ":" + this.hashCode();
        injectionDAO.save(arg);
    }
}
