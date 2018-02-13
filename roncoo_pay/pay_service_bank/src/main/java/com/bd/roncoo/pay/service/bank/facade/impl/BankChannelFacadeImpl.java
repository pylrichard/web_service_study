package com.bd.roncoo.pay.service.bank.facade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bankChannelFacade")
public class BankChannelFacadeImpl implements BankChannelFacade {
    @Autowired
    private BankChannelBiz bankChannelBiz;

    @Override
    public BankChannel getByBankChannelCode(String bankChannelCode) {
        return bankChannelBiz.getByBankChannelCode(bankChannelCode);
    }

    @Override
    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
        return bankChannelBiz.listPage(pageParam, paramMap);
    }

    @Override
    public BankChannel getById(long id) {
        return bankChannelBiz.getById(id);
    }

    @Override
    public long update(BankChannel entity) {
        saveCacheBankChannel(entity);

        return bankChannelBiz.update(entity);
    }

    @Override
    public long create(BankChannel entity) {
        saveCacheBankChannel(entity);

        return bankChannelBiz.create(entity);
    }

    /**
     * 缓存银行渠道
     */
    private void saveCacheBankChannel(BankChannel entity) {
        StringBuffer buffer = new StringBuffer(CacheConstant.BANK_CHANNEL).append(entity.getBankChannelCode());
        RedisUtils.save(buffer.toString(), entity);
    }

    @Override
    public void deleteChannelByCode(String bankChannelCode) {
        bankChannelBiz.deleteChannelByCode(bankChannelCode);
    }

    @Override
    public BankChannel getByBankChannelName(String channelName) {
        return bankChannelBiz.getByBankChannelName(channelName);
    }

    @Override
    public BankChannel getByBankAgreementId(long bankAgreementId) {
        return bankChannelBiz.getByBankAgreementId(bankAgreementId);
    }

    @Override
    public List<BankChannel> listBy(Map<String, Object> paramMap) {
        return bankChannelBiz.listBy(paramMap);
    }

    /**
     * 根据协议表中的业务类型和账户表中的账户性质查询对应的渠道
     */
    public List<BankChannel> listChannalByAgreementBusTypeAndAccountType(int linkType, int accountType) {
        return bankChannelBiz.listChannalByAgreementBusTypeAndAccountType(linkType, accountType);
    }

    @Override
    public boolean isUsableBankChannel(String bankChannelCode) {
        return bankChannelBiz.isUsableBankChannel(bankChannelCode);
    }

    /**
     * 根据银行渠道编号模糊查找
     */
    public List likeBy(String bankChannelCode, Integer status) {
        return bankChannelBiz.likeBy(bankChannelCode, status);
    }
}
