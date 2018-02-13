package com.bd.roncoo.pay.service.bank.core.biz;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 银行结算信息管理业务逻辑层实现
 */
@Component("bankSettlementBiz")
public class BankSettlementBiz extends BaseBizImpl<BankSettlement> {
    @Autowired
    private BankSettlementDao bankSettlementDao;

    @Override
    protected BaseDao<BankSettlement> getDao() {
        return bankSettlementDao;
    }

    public List listAvailableBankSettlementInfo() {
        return bankSettlementDao.listAvailableBankSettlementInfo();
    }

    /**
     * 根据银行渠道编号获取银行结算信息
     *
     * @param bankChannelCode 银行渠道编号
     */
    public BankSettlement getByBankBankChannelCode(String bankChannelCode) {
        if (StringUtils.isBlank(bankChannelCode)) {
            throw new BankBizException(BankBizException.BANK_SERVICE_PARAMS_ERROR, "银行渠道编号不能为空:%s", bankChannelCode);
        }

        return bankSettlementDao.getByBankBankChannelCode(bankChannelCode);
    }

    /**
     * 获取银行渠道可用状态下的银行账号
     */
    public List listAvailableBankAccount() {
        return bankSettlementDao.listAvailableBankAccount();
    }
}
