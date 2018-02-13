package com.bd.roncoo.pay.service.bank.core.dao;

public interface CardBinDao extends BaseDao<CardBin> {
    CardBin getByCardBin(String cardBin, Integer status);
}