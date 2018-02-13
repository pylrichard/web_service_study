package com.bd.roncoo.pay.service.bank.core.dao;

public interface BankAgreementDao extends BaseDao<BankAgreement> {
    /**
     * 根据商户编号查找银行协议
     */
    BankAgreement getByMerchantNo(String merchantNo);
}