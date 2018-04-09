package com.bd.ace.trade.common.api;

import com.bd.ace.trade.common.protocol.user.ChangeUserMoneyReq;
import com.bd.ace.trade.common.protocol.user.ChangeUserMoneyRes;
import com.bd.ace.trade.common.protocol.user.QueryUserReq;
import com.bd.ace.trade.common.protocol.user.QueryUserRes;

public interface IUserApi {
	 QueryUserRes queryUser(QueryUserReq queryUserReq);
	 ChangeUserMoneyRes changeUserMoney(ChangeUserMoneyReq changeUserMoneyReq);
}
