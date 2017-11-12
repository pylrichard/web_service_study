package com.bd.imooc.mmall.controller.portal;

import com.bd.imooc.mmall.common.Const;
import com.bd.imooc.mmall.common.ServerResponse;
import com.bd.imooc.mmall.pojo.Shipping;
import com.bd.imooc.mmall.service.ShippingService;
import com.github.pagehelper.PageInfo;
import com.bd.imooc.mmall.common.ResponseCode;
import com.bd.imooc.mmall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    private ShippingService shippingService;

    @RequestMapping("add.do")
    public ServerResponse add(HttpSession session, Shipping shipping) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                        ResponseCode.NEED_LOGIN.getDesc());
        }

        return shippingService.add(user.getId(), shipping);
    }


    @RequestMapping("del.do")
    public ServerResponse del(HttpSession session, Integer shippingId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                        ResponseCode.NEED_LOGIN.getDesc());
        }

        return shippingService.del(user.getId(), shippingId);
    }

    @RequestMapping("update.do")
    public ServerResponse update(HttpSession session, Shipping shipping) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                        ResponseCode.NEED_LOGIN.getDesc());
        }

        return shippingService.update(user.getId(), shipping);
    }


    @RequestMapping("select.do")
    public ServerResponse<Shipping> select(HttpSession session, Integer shippingId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                        ResponseCode.NEED_LOGIN.getDesc());
        }

        return shippingService.select(user.getId(), shippingId);
    }


    @RequestMapping("list.do")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10")int pageSize,
                                         HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                        ResponseCode.NEED_LOGIN.getDesc());
        }

        return shippingService.list(user.getId(), pageNum, pageSize);
    }
}
