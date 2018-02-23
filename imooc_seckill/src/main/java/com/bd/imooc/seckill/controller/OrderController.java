package com.bd.imooc.seckill.controller;

import com.bd.imooc.seckill.domain.OrderInfo;
import com.bd.imooc.seckill.domain.SecKillUser;
import com.bd.imooc.seckill.result.CodeMsg;
import com.bd.imooc.seckill.result.Result;
import com.bd.imooc.seckill.service.GoodsService;
import com.bd.imooc.seckill.service.OrderService;
import com.bd.imooc.seckill.service.RedisService;
import com.bd.imooc.seckill.service.SecKillUserService;
import com.bd.imooc.seckill.vo.GoodsVo;
import com.bd.imooc.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    SecKillUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, SecKillUser user,
                                      @RequestParam("orderId") long orderId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);

        return Result.success(vo);
    }
}
