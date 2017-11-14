package com.bd.ace.trade.common.api;

import com.bd.ace.trade.common.protocol.order.ConfirmOrderReq;
import com.bd.ace.trade.common.protocol.order.ConfirmOrderRes;

public interface IOrderApi {
    ConfirmOrderRes confirmOrder(ConfirmOrderReq confirmOrderReq);
}
