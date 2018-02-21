package com.bd.roncoo.pay.facade.bank.service;

import com.bd.roncoo.pay.facade.bank.entity.BankChannel;
import com.bd.roncoo.pay.facade.bank.exceptions.BankBizException;

import java.util.List;
import java.util.Map;

/**
 * 银行渠道信息Dubbo服务接口
 */
public interface BankChannelFacade {
    /**
     * 根据银行渠道编号查找银行渠道信息
     */
    BankChannel getByBankChannelCode(String bankChannelCode) throws BankBizException;

    /**
     * 分页查询
     */
    PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BankBizException;

    /**
     * 根据银行渠道ID获取银行渠道信息
     *
     * @param id 银行渠道ID
     */
    BankChannel getById(long id) throws BankBizException;

    long update(BankChannel entity) throws BankBizException;

    long create(BankChannel entity) throws BankBizException;

    /**
     * 根据银行渠道编号删除银行渠道信息
     */
    void deleteChannelByCode(String bankChannelCode) throws BankBizException;

    /**
     * 根据银行渠道判断银行渠道是否可用
     */
    boolean isUsableBankChannel(String bankChannelCode) throws BankBizException;

    /***
     * 根据银行渠道名称查询银行渠道信息
     */
    BankChannel getByBankChannelName(String channelName) throws BankBizException;

    /**
     * 根据银行协议ID获取银行渠道信息
     */
    BankChannel getByBankAgreementId(long bankAgreementId) throws BankBizException;

    List<BankChannel> listBy(Map<String, Object> paramMap) throws BankBizException;

    /**
     * 根据协议表中的业务类型和账户表中的账户性质查询对应的渠道
     */
    List<BankChannel> listChannalByAgreementBusTypeAndAccountType(int linkType, int accountType)
            throws BankBizException;

    /**
     * 根据银行渠道编号模糊查找
     */
    List likeBy(String bankChannelCode, Integer status) throws BankBizException;
}
