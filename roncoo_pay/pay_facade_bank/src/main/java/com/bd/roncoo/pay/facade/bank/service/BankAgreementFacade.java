package com.bd.roncoo.pay.facade.bank.service;

import com.bd.roncoo.pay.facade.bank.entity.BankAgreement;
import com.bd.roncoo.pay.facade.bank.exceptions.BankBizException;

import java.util.Map;

public interface BankAgreementFacade {
	long create(BankAgreement entity) throws BankBizException;

	long update(BankAgreement entity) throws BankBizException;

	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BankBizException;

	BankAgreement getById(long id) throws BankBizException;
}
