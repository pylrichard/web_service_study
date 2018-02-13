package com.bd.roncoo.pay.service.bank.core.dao;

import java.util.List;

/**
 * 银行账户信息表数据访问层接口
 */
public interface BankAccountDao extends BaseDao<BankAccount> {
    /**
     * 根据银行账号模糊查找
     */
    List<BankAccount> likeBy(String bankAccountKey, Integer status);
}