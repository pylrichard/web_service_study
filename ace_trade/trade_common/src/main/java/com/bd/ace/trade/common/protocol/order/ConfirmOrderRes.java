package com.bd.ace.trade.common.protocol.order;

import com.bd.ace.trade.common.protocol.BaseRes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmOrderRes extends BaseRes {
    private String orderId;
}
