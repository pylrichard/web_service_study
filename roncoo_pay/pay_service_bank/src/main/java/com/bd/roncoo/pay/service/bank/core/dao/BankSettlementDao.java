package com.bd.roncoo.pay.service.bank.core.dao;

import java.util.List;

/**
 * 银行结算信息管理数据访问层接口
 */
public interface BankSettlementDao extends BaseDao<BankSettlement> {
    /**
     * 根据银行渠道编号获取银行结算信息
     *
     * @param bankChannelCode 银行渠道编号
     */
    BankSettlement getByBankBankChannelCode(String bankChannelCode);

    List listAvailableBankSettlementInfo();

    /**
     * 获取银行渠道可用状态下的银行账号
     */
    List listAvailableBankAccount();
}