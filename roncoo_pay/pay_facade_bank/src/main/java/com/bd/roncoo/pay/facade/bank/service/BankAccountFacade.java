package com.bd.roncoo.pay.facade.bank.service;

import com.bd.roncoo.pay.facade.bank.entity.BankAccount;
import com.bd.roncoo.pay.facade.bank.exceptions.BankBizException;

import java.util.List;
import java.util.Map;

/**
 * 银行账户信息Dubbo服务接口
 */
public interface BankAccountFacade {
	long create(BankAccount entity) throws BankBizException;

	long update(BankAccount entity) throws BankBizException;

	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BankBizException;

	BankAccount getById(long id) throws BankBizException;
	
	/**
	 * 根据银行账号查询银行账户信息
	 */
	BankAccount getByBankAccount(String bankAccount) throws BankBizException;
	
	/**
	 * 根据开户银行查询银行账户信息
     *
	 * @param openBank 开户银行
	 */
	List<BankAccount> getByOpenBank(String openBank) throws BankBizException;

	/**
	 * 根据ID删除银行账户信息
	 * 
	 * @param id 主键
	 */
	void deleteById(long id) throws BankBizException;
	
	/**
	 * 根据银行账号模糊查找 
	 */
	List<BankAccount> likeBy(String bankAccountKey, Integer status)  throws BankBizException;
}
