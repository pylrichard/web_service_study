package com.bd.imooc.mmall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.bd.imooc.mmall.common.Const;
import com.bd.imooc.mmall.common.ResponseCode;
import com.bd.imooc.mmall.common.ServerResponse;
import com.bd.imooc.mmall.pojo.User;
import com.bd.imooc.mmall.service.OrderService;
import com.bd.imooc.mmall.service.impl.OrderServiceImpl;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/order/")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping("create.do")
    public ServerResponse createOrder(HttpSession session, Integer shippingId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                        ResponseCode.NEED_LOGIN.getDesc());
        }

        return orderService.createOrder(user.getId(), shippingId);
    }

    @RequestMapping("cancel.do")
    public ServerResponse cancelOrder(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                        ResponseCode.NEED_LOGIN.getDesc());
        }

        return orderService.cancelOrder(user.getId(), orderNo);
    }

    @RequestMapping("get_cart_checked_product_detail.do")
    public ServerResponse getCartCheckedProductDetail(HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                        ResponseCode.NEED_LOGIN.getDesc());
        }

        return orderService.getCartCheckedProductDetail(user.getId());
    }

    @RequestMapping("get_order_detail.do")
    public ServerResponse getOrderDetail(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                        ResponseCode.NEED_LOGIN.getDesc());
        }

        return orderService.getOrderDetail(user.getId(), orderNo);
    }

    @RequestMapping("get_user_all_order.do")
    public ServerResponse getUserAllOrder(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1")int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                        ResponseCode.NEED_LOGIN.getDesc());
        }

        return orderService.getUserAllOrder(user.getId(), pageNum, pageSize);
    }

    @RequestMapping("pay.do")
    public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                        ResponseCode.NEED_LOGIN.getDesc());
        }
        //获取当面付二维码图片路径
        String path = request.getSession().getServletContext().getRealPath("upload");

        return orderService.pay(orderNo, user.getId(), path);
    }

    /**
     * 由支付宝进行回调，见mmall.properties
     */
    @RequestMapping("alipay_callback.do")
    public Object alipayCallback(HttpServletRequest request) {
        /*
            解析响应参数存放在HashMap
         */
        Map<String, String> params = Maps.newHashMap();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0 ; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        logger.info("支付宝回调，sign:{}，trade_status:{}，参数:{}", params.get("sign"),
                params.get("trade_status"), params.toString());
        /*
            验证回调请求是否是支付宝发出的，避免重复通知
         */
        params.remove("sign_type");
        try {
            /*
                注意rsaCheck()对RSA和RSA2进行了区分判断，此处使用RSA2
             */
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(),
                                                            "utf-8", Configs.getSignType());
            if (!alipayRSACheckedV2) {
                return ServerResponse.createByErrorMessage("非法请求，验证不通过");
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝验证回调异常", e);
        }
        //验证回调请求参数
        ServerResponse serverResponse = orderService.alipayCallback(params);
        if (serverResponse.isSuccess()) {
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }

        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    @RequestMapping("query_order_pay_status.do")
    public ServerResponse<Boolean> queryOrderPayStatus(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                        ResponseCode.NEED_LOGIN.getDesc());
        }
        ServerResponse serverResponse = orderService.queryOrderPayStatus(user.getId(), orderNo);
        if (serverResponse.isSuccess()) {
            return ServerResponse.createBySuccess(true);
        }

        return ServerResponse.createBySuccess(false);
    }
}
