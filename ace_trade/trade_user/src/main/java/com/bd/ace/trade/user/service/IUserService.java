package com.bd.ace.trade.user.service;

import com.bd.ace.trade.common.protocol.user.ChangeUserMoneyReq;
import com.bd.ace.trade.common.protocol.user.ChangeUserMoneyRes;
import com.bd.ace.trade.common.protocol.user.QueryUserReq;
import com.bd.ace.trade.common.protocol.user.QueryUserRes;

public interface IUserService {
    QueryUserRes queryUser(QueryUserReq queryUserReq);
    ChangeUserMoneyRes changeUserMoney(ChangeUserMoneyReq changeUserMoneyReq);
}
