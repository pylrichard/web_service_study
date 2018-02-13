package com.bd.roncoo.pay.service.bank.core.biz;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 银行卡CardBin信息管理业务逻辑
 */
@Component("cardBinBiz")
public class CardBinBiz extends BaseBizImpl<CardBin> {
    @Autowired
    private CardBinDao cardBinDao;

    @Override
    protected BaseDao<CardBin> getDao() {
        return cardBinDao;
    }

    public CardBin getByCardBin(String cardBin, Integer status) {
        if (StringUtils.isBlank(cardBin)) {
            throw new BankBizException(BankBizException.BANK_SERVICE_PARAMS_ERROR, "cardBin不能为空:%s", cardBin);
        }

        return cardBinDao.getByCardBin(cardBin, status);
    }
}
