package com.imooc.mmall.dao;

import com.imooc.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<Cart> selectCartByUserId(Integer userId);

    int selectCartProductCheckedStatusByUserId(Integer userId);

    Cart selectCartByUserIdProductId(@Param("userId")Integer userId, @Param("productId")Integer productId);

    int deleteByUserIdProductIds(@Param("userId")Integer userId, @Param("productIdList")List<String> productIdList);

    int checkedOrUncheckedProduct(@Param("userId")Integer userId, @Param("productId")Integer productId, @Param("checked")Integer checked);

    int selectCartProductCount(@Param("userId")Integer userId);

    List<Cart> selectCheckedCartByUserId(Integer userId);
}