package com.bd.roncoo.pay.service.bank.facade.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 银行卡CardBin信息Dubbo服务接口实现
 */
@Component("cardBinFacade")
public class CardBinFacadeImpl implements CardBinFacade {
    @Autowired
    private CardBinBiz cardBinBiz;

    @Override
    public long create(CardBin cardBin) {
        return cardBinBiz.create(cardBin);
    }

    @Override
    public long update(CardBin cardBin) {
        return cardBinBiz.update(cardBin);
    }

    @Override
    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
        return cardBinBiz.listPage(pageParam, paramMap);
    }

    @Override
    public CardBin getById(long id) {
        return cardBinBiz.getById(id);
    }

    public CardBin getByCardBin(String cardBin, Integer status) {
        return cardBinBiz.getByCardBin(cardBin, status);
    }
}
