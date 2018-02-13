package com.bd.roncoo.pay.service.bank.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 银行账户信息表数据访问层接口实现
 */
@Repository("bankAccountDao")
public class BankAccountDaoImpl extends BaseDaoImpl<BankAccount> implements BankAccountDao {
    /**
     * 根据银行账号模糊查找
     */
    public List<BankAccount> likeBy(String bankAccountKey, Integer status) {
        Map<String, Object> params = new HashMap<>();
        params.put("bankAccountKey", bankAccountKey);
        params.put("status", status);

        return super.getSessionTemplate().selectList(getStatement("likeBy"), params);
    }
}