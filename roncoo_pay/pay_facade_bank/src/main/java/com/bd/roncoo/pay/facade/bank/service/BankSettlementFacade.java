package com.bd.roncoo.pay.facade.bank.service;

import com.bd.roncoo.pay.facade.bank.entity.BankSettlement;
import com.bd.roncoo.pay.facade.bank.exceptions.BankBizException;

import java.util.List;
import java.util.Map;

/**
 * 银行结算信息管理Dubbo服务接口
 */
public interface BankSettlementFacade {
	long create(BankSettlement entity) throws BankBizException;

	long update(BankSettlement entity) throws BankBizException;

	/**
	 * 分页查询银行结算信息
	 * 
	 * @param pageParam 分页实体对象
	 * @param paramMap 查询条件
	 */
	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BankBizException;

	BankSettlement getById(long id) throws BankBizException;

	/**
	 * 根据银行渠道编号获取银行结算信息
	 * 
	 * @param bankChannelCode 银行渠道编号
	 */
	BankSettlement getByBankBankChannelCode(String bankChannelCode) throws BankBizException;

	/**
	 * 获取银行渠道状态为可用的银行结算信息，包括以下字段内容：
	 * bankChannelCode 银行渠道编号
	 * tradeGainCheckFileTime 业务对账文件获取时间，如1.15代表每天凌晨1点15分后获取对账文件
	 * fundGainCheckFileTime 清算对账文件获取时间，如1.15代表每天凌晨1点15分后获取对账文件
	 * settleCycle 结算周期：T+X
	 */
	List listAvailableBankSettlementInfo() throws BankBizException;
	
	/**
	 * 获取银行渠道可用状态下的银行账号
	 */
	List listAvailableBankAccount() throws BankBizException;
}
