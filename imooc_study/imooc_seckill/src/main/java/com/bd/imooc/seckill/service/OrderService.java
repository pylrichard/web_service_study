package com.bd.imooc.seckill.service;

import com.bd.imooc.seckill.dao.OrderDao;
import com.bd.imooc.seckill.domain.OrderInfo;
import com.bd.imooc.seckill.domain.SecKillOrder;
import com.bd.imooc.seckill.domain.SecKillUser;
import com.bd.imooc.seckill.redis.OrderKey;
import com.bd.imooc.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    public SecKillOrder getSecKillOrderByUserIdGoodsId(long userId, long goodsId) {
        return redisService.get(OrderKey.getSecKillOrderByUidGid,
                "" + userId + "_" + goodsId, SecKillOrder.class);
    }

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderInfo createOrder(SecKillUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getSecKillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderDao.insert(orderInfo);
        SecKillOrder secKillOrder = new SecKillOrder();
        secKillOrder.setGoodsId(goods.getId());
        secKillOrder.setOrderId(orderInfo.getId());
        secKillOrder.setUserId(user.getId());
        orderDao.insertSecKillOrder(secKillOrder);
        redisService.set(OrderKey.getSecKillOrderByUidGid,
                "" + user.getId() + "_" + goods.getId(), secKillOrder);

        return orderInfo;
    }

    public void deleteOrders() {
        orderDao.deleteOrders();
        orderDao.deleteSecKillOrders();
    }
}
