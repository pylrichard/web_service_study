package com.imooc.mmall.service;

import com.imooc.mmall.common.ServerResponse;
import com.imooc.mmall.vo.CartVo;

public interface CartService {
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVo> list(Integer userId);
    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);
    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
