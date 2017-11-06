package com.imooc.mmall.dao;

import com.imooc.mmall.pojo.Cart;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    /**
     * Cart的非空成员变量才进行insert，实现见CartMapper.xml
     */
    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
}