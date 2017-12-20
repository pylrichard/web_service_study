package com.bd.imooc.spring.ioc.injection.service;

import com.bd.imooc.spring.ioc.injection.dao.InjectionDAO;

public class InjectionServiceImpl implements InjectionService {
    private InjectionDAO injectionDAO;

    /**
     * 构造注入，见5.png
     * 此处参数名要与spring-injection.xml中保持一致
     */
    public InjectionServiceImpl(InjectionDAO injectionDAO) {
        this.injectionDAO = injectionDAO;
    }

    /**
     * 设值注入，Spring IoC自动调用此方法，见4.png
     */
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
