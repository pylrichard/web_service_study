package com.bd.roncoo.pay.facade.bank.service;

import com.bd.roncoo.pay.facade.bank.entity.CardBin;
import com.bd.roncoo.pay.facade.bank.exceptions.BankBizException;

import java.util.Map;

/**
 * 银行卡CardBin信息Dubbo服务接口
 */
public interface CardBinFacade {
	long create(CardBin entity) throws BankBizException;

	long update(CardBin entity) throws BankBizException;

	/**
	 * 分页查询
	 * 
	 * @param pageParam 分页实体对象
	 * @param paramMap 查询条件
	 */
	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BankBizException;

	CardBin getById(long id) throws BankBizException;

	CardBin getByCardBin(String cardBin, Integer status) throws BankBizException;
}
