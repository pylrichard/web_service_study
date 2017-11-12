package com.bd.imooc.mmall.service;

import com.bd.imooc.mmall.common.ServerResponse;
import com.bd.imooc.mmall.vo.OrderVo;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface OrderService {
    /**
     * portal订单接口
     */
    ServerResponse createOrder(Integer userId, Integer shippingId);
    ServerResponse<String> cancelOrder(Integer userId, Long orderNo);
    ServerResponse getCartCheckedProductDetail(Integer userId);
    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);
    ServerResponse<PageInfo> getUserAllOrder(Integer userId, int pageNum, int pageSize);
    ServerResponse pay(Long orderNo, Integer userId, String path);
    ServerResponse alipayCallback(Map<String, String> params);
    ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);

    /**
     * backend订单接口
     */
    ServerResponse<PageInfo> getAllOrder(int pageNum, int pageSize);
    ServerResponse<OrderVo> getOrderDetail(Long orderNo);
    ServerResponse<PageInfo> searchOrder(Long orderNo, int pageNum, int pageSize);
    ServerResponse<String> sendOrderGoods(Long orderNo);
}
