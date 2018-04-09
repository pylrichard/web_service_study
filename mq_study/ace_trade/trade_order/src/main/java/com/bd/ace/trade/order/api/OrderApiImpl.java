package com.bd.ace.trade.order.api;

import com.bd.ace.trade.common.api.IOrderApi;
import com.bd.ace.trade.common.protocol.order.ConfirmOrderReq;
import com.bd.ace.trade.common.protocol.order.ConfirmOrderRes;
import com.bd.ace.trade.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderApiImpl implements IOrderApi {
    @Autowired
    private IOrderService orderService;

    @Override
    @PostMapping("/confirmOrder")
    public ConfirmOrderRes confirmOrder(@RequestBody ConfirmOrderReq confirmOrderReq) {
        return orderService.confirmOrder(confirmOrderReq);
    }
}
