package com.bd.roncoo.pay.service.bank.core.dao;

import java.util.List;
import java.util.Map;

public interface BankChannelDao extends BaseDao<BankChannel> {
    /**
     * 根据银行渠道编号查找
     */
    BankChannel getByBankChannelCode(String bankChannelCode);

    /**
     * 根据银行渠道编号模糊查找
     */
    List<BankChannel> likeBy(String bankChannelCode, Integer status);

    void deleteChannelByCode(String bankChannelCode);

    /**
     * 根据银行渠道名称查询银行渠道信息
     */
    BankChannel getByBankChannelName(String channelName);

    /**
     * 根据银行协议ID获取银行渠道
     */
    BankChannel getByBankAgreementId(long bankAgreementId);

    List listBy(Map<String, Object> param);

    /**
     * 根据协议表中的业务类型和账户表中的账户性质查询对应的渠道
     */
    List<BankChannel> listChannelByAgreementBusTypeAndAccountType(int linkType, int accountType);

    boolean isUsableBankChannel(String bankChannelCode);
}