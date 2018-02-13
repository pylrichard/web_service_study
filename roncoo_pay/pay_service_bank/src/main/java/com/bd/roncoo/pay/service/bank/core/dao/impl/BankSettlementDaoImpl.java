package com.bd.roncoo.pay.service.bank.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 银行结算信息管理数据访问层接口实现
 */
@Repository(value = "bankSettlementDao")
public class BankSettlementDaoImpl extends BaseDaoImpl<BankSettlement> implements BankSettlementDao {
    @Override
    public List listAvailableBankSettlementInfo() {
        return super.getSessionTemplate().selectList("listAvailableBankSettlementInfo", null);
    }

    /**
     * 根据银行渠道编号获取银行结算信息
     */
    public BankSettlement getByBankChannelCode(String bankChannelCode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("bankChannelCode", bankChannelCode);

        return super.getSessionTemplate().selectOne("getByBankChannelCode", params);
    }

    /**
     * 获取银行渠道可用状态下的银行账号
     */
    @Override
    public List listAvailableBankAccount() {
        return super.getSessionTemplate().selectList("listAvailableBankAccount", null);
    }
}