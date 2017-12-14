package com.bd.imooc.mmall.service;

import com.bd.imooc.mmall.common.ServerResponse;
import com.bd.imooc.mmall.vo.CartVo;

public interface CartService {
    ServerResponse<CartVo> addProduct(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> getCartProductList(Integer userId);

    ServerResponse<CartVo> updateProduct(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);
    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
