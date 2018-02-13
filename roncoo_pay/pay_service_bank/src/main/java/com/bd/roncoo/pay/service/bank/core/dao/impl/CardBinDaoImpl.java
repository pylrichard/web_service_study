package com.bd.roncoo.pay.service.bank.core.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository(value = "cardBinDao")
public class CardBinDaoImpl extends BaseDaoImpl<CardBin> implements CardBinDao {
    public CardBin getByCardBin(String cardBin, Integer status) {
        Map<String, Object> params = new HashMap<>();
        params.put("cardBin", cardBin);
        params.put("status", status);

        return super.getBy(params);
    }
}