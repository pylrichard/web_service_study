package com.bd.ace.trade.pay.api;

import com.bd.ace.trade.common.api.IPayApi;
import com.bd.ace.trade.common.constant.TradeEnums;
import com.bd.ace.trade.common.protocol.pay.CallbackPaymentReq;
import com.bd.ace.trade.common.protocol.pay.CallbackPaymentRes;
import com.bd.ace.trade.common.protocol.pay.CreatePaymentReq;
import com.bd.ace.trade.common.protocol.pay.CreatePaymentRes;
import com.bd.ace.trade.pay.service.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PayApiImpl implements IPayApi {
    @Autowired
    private IPayService payService;

    @Override
    @PostMapping("/createPayment")
    public CreatePaymentRes createPayment(@RequestBody CreatePaymentReq createPaymentReq) {
        return payService.createPayment(createPaymentReq);
    }

    @Override
    @PostMapping("/callbackPayment")
    public CallbackPaymentRes callbackPayment(@RequestBody CallbackPaymentReq callbackPaymentReq) {
        CallbackPaymentRes callbackPaymentRes = new CallbackPaymentRes();
        try {
            callbackPaymentRes = payService.callbackPayment(callbackPaymentReq);
        } catch (Exception e) {
            callbackPaymentRes.setRetCode(TradeEnums.RetEnum.FAIL.getCode());
            callbackPaymentRes.setRetInfo(TradeEnums.RetEnum.FAIL.getDesc());
        }

        return callbackPaymentRes;
    }
}
