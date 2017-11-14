package com.bd.ace.trade.common.api;

import com.bd.ace.trade.common.protocol.pay.CallbackPaymentReq;
import com.bd.ace.trade.common.protocol.pay.CallbackPaymentRes;
import com.bd.ace.trade.common.protocol.pay.CreatePaymentReq;
import com.bd.ace.trade.common.protocol.pay.CreatePaymentRes;

public interface IPayApi {
    CreatePaymentRes createPayment(CreatePaymentReq createPaymentReq);
    CallbackPaymentRes callbackPayment(CallbackPaymentReq callbackPaymentReq);
}
