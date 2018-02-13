package com.bd.roncoo.pay.service.bank.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bankCacheFacade")
public class BankCacheFacadeImpl implements BankCacheFacade {
    @Autowired
    private BankChannelBiz bankChannelBiz;

    /**
     * 根据银行渠道编号从缓存中查找银行渠道信息
     */
    @Override
    public BankChannel getBankChannelByChannelCodeInCache(String bankChannelCode)
            throws BankBizException {
        StringBuffer buffer = new StringBuffer(CacheConstant.BANK_CHANNEL).append(bankChannelCode);
        BankChannel bankChannel = (BankChannel) RedisUtils.get(buffer.toString());
        if (bankChannel == null) {
            bankChannel = bankChannelBiz.getByBankChannelCode(bankChannelCode);
            if (bankChannel == null) {
                return null;
            }
            RedisUtils.save(buffer.toString(), bankChannel);
        }

        return bankChannel;
    }
}
