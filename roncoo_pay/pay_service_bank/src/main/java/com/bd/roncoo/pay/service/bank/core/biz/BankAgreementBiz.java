package com.bd.roncoo.pay.service.bank.core.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bankAgreementBiz")
public class BankAgreementBiz extends BaseBizImpl<BankAgreement> {
    @Autowired
    private BankAgreementDao bankAgreementDao;

    @Override
    protected BaseDao<BankAgreement> getDao() {
        return bankAgreementDao;
    }
}
