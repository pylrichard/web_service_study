package com.imooc.mmall.controller.portal;

import com.imooc.mmall.common.Const;
import com.imooc.mmall.common.ResponseCode;
import com.imooc.mmall.common.ServerResponse;
import com.imooc.mmall.pojo.User;
import com.imooc.mmall.service.CartService;
import com.imooc.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("list.do")
    public ServerResponse<CartVo> list(HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user ==null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                        ResponseCode.NEED_LOGIN.getDesc());
        }

        return cartService.list(user.getId());
    }

    @RequestMapping("add.do")
    public ServerResponse<CartVo> add(HttpSession session, Integer count, Integer productId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user ==null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                        ResponseCode.NEED_LOGIN.getDesc());
        }

        return cartService.add(user.getId(), productId, count);
    }
}