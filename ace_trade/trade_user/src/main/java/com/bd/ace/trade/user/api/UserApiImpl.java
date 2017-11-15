package com.bd.ace.trade.user.api;

import com.bd.ace.trade.common.api.IUserApi;
import com.bd.ace.trade.common.constant.TradeEnums;
import com.bd.ace.trade.common.protocol.user.ChangeUserMoneyReq;
import com.bd.ace.trade.common.protocol.user.ChangeUserMoneyRes;
import com.bd.ace.trade.common.protocol.user.QueryUserReq;
import com.bd.ace.trade.common.protocol.user.QueryUserRes;
import com.bd.ace.trade.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class UserApiImpl implements IUserApi {
    @Autowired
    private IUserService userService;

    @Override
    @PostMapping("/queryUser")
    public QueryUserRes queryUser(@RequestBody QueryUserReq queryUserReq) {
        if (queryUserReq == null || queryUserReq.getUserId() == null) {
            throw new RuntimeException("请求参数不正确");
        }

        return userService.queryUser(queryUserReq);
    }

    @Override
    public ChangeUserMoneyRes changeUserMoney(@RequestBody ChangeUserMoneyReq changeUserMoneyReq) {
        if (changeUserMoneyReq == null || changeUserMoneyReq.getUserId() == null
                || changeUserMoneyReq.getUserMoney() == null) {
            throw new RuntimeException("请求参数不一致");
        }
        if (changeUserMoneyReq.getUserMoney().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("余额不能小于0");
        }
        ChangeUserMoneyRes changeUserMoneyRes = new ChangeUserMoneyRes();
        try {
            changeUserMoneyRes = this.userService.changeUserMoney(changeUserMoneyReq);
        } catch (Exception ex) {
            changeUserMoneyRes.setRetCode(TradeEnums.RetEnum.FAIL.getCode());
            changeUserMoneyRes.setRetInfo(ex.getMessage());
        }

        return changeUserMoneyRes;
    }
}
