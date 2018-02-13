package com.bd.roncoo.pay.service.bank.core.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository(value = "bankAgreementDao")
public class BankAgreementDaoImpl extends BaseDaoImpl<BankAgreement> implements BankAgreementDao {
    /**
     * 根据商户编号查找银行协议
     */
    public BankAgreement getByMerchantNo(String merchantNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantNo", merchantNo);

        return super.getBy(params);
    }
}