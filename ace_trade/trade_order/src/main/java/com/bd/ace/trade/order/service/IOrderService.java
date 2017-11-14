package com.bd.ace.trade.order.service;

import com.bd.ace.trade.common.protocol.order.ChangeOrderStatusReq;
import com.bd.ace.trade.common.protocol.order.ChangeOrderStatusRes;
import com.bd.ace.trade.common.protocol.order.ConfirmOrderReq;
import com.bd.ace.trade.common.protocol.order.ConfirmOrderRes;

public interface IOrderService {
    public ConfirmOrderRes confirmOrder(ConfirmOrderReq confirmOrderReq);
    public ChangeOrderStatusRes changeOrderStatus(ChangeOrderStatusReq changeOrderStatusReq);
}
