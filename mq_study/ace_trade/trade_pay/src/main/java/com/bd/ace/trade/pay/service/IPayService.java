package com.bd.ace.trade.pay.service;

import com.bd.ace.trade.common.protocol.pay.CallbackPaymentReq;
import com.bd.ace.trade.common.protocol.pay.CallbackPaymentRes;
import com.bd.ace.trade.common.protocol.pay.CreatePaymentReq;
import com.bd.ace.trade.common.protocol.pay.CreatePaymentRes;

public interface IPayService {
    CreatePaymentRes createPayment(CreatePaymentReq createPaymentReq);
    CallbackPaymentRes callbackPayment(CallbackPaymentReq callbackPaymentReq);
}
