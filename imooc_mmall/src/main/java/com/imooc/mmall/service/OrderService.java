package com.imooc.mmall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mmall.common.ServerResponse;
import com.imooc.mmall.vo.OrderVo;

public interface OrderService {
    /**
     * portal订单接口
     */
    ServerResponse createOrder(Integer userId, Integer shippingId);
    ServerResponse<String> cancelOrder(Integer userId, Long orderNo);
    ServerResponse getCartCheckedProductDetail(Integer userId);
    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);
    ServerResponse<PageInfo> getUserAllOrder(Integer userId, int pageNum, int pageSize);

    /**
     * backend订单接口
     */
    ServerResponse<PageInfo> getAllOrder(int pageNum, int pageSize);
    ServerResponse<OrderVo> getOrderDetail(Long orderNo);
    ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize);
    ServerResponse<String> sendOrderGoods(Long orderNo);
}
