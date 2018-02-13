package com.bd.roncoo.pay.service.bank.facade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bankSettlementFacade")
public class BankSettlementFacadeImpl implements BankSettlementFacade {
    @Autowired
    private BankSettlementBiz bankSettlementBiz;

    @Override
    public long create(BankSettlement bankSettlement) {
        return bankSettlementBiz.create(bankSettlement);
    }

    @Override
    public long update(BankSettlement bankSettlement) {
        return bankSettlementBiz.update(bankSettlement);
    }

    @Override
    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
        return bankSettlementBiz.listPage(pageParam, paramMap);
    }

    @Override
    public BankSettlement getById(long id) {
        return bankSettlementBiz.getById(id);
    }

    @Override
    public List listAvailableBankSettlementInfo() {
        return bankSettlementBiz.listAvailableBankSettlementInfo();
    }

    @Override
    public BankSettlement getByBankBankChannelCode(String bankChannelCode) {
        return bankSettlementBiz.getByBankBankChannelCode(bankChannelCode);
    }

    @Override
    public List listAvailableBankAccount() throws BizException {
        return bankSettlementBiz.listAvailableBankAccount();
    }
}
