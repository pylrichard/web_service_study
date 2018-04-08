package com.bd.imooc.seckill.rabbitmq;

import com.bd.imooc.seckill.domain.SecKillOrder;
import com.bd.imooc.seckill.domain.SecKillUser;
import com.bd.imooc.seckill.service.GoodsService;
import com.bd.imooc.seckill.service.OrderService;
import com.bd.imooc.seckill.service.RedisService;
import com.bd.imooc.seckill.service.SecKillService;
import com.bd.imooc.seckill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqReceiver {
    private static Logger log = LoggerFactory.getLogger(MqReceiver.class);

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SecKillService secKillService;

    @RabbitListener(queues = MqConfig.SEC_KILL_QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
        SecKillMessage mm = RedisService.stringToBean(message, SecKillMessage.class);
        SecKillUser user = mm.getUser();
        long goodsId = mm.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return;
        }
        //判断是否已经秒杀成功
        SecKillOrder order = orderService.getSecKillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }
        //减库存，生成秒杀订单
        secKillService.createOrder(user, goods);
    }
}
