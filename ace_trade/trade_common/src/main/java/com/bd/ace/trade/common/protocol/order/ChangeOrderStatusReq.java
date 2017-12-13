package com.bd.ace.trade.common.protocol.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeOrderStatusReq {
    private String orderStatus;
    private String orderId;
}
