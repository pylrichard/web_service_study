package com.bd.imooc.spring.primer.ioc.injection.dao;

public class InjectionDAOImpl implements InjectionDAO {
    @Override
    public void save(String arg) {
        //模拟数据库保存操作
        System.out.println("保存数据：" + arg);
    }
}
