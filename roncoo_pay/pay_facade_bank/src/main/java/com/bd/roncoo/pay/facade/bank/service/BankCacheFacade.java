package com.bd.roncoo.pay.facade.bank.service;

import com.bd.roncoo.pay.facade.bank.entity.BankChannel;
import com.bd.roncoo.pay.facade.bank.exceptions.BankBizException;

/**
 * 银行服务功能缓存接口
 */
public interface BankCacheFacade {
	/**
	 * 根据银行渠道编号从缓存中查找银行渠道信息
	 */
	BankChannel getBankChannelByChannelCodeInCache(String bankChannelCode) throws BankBizException;
}
