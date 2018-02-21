package com.bd.roncoo.pay.facade.bank.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 银行协议参数实体
 */
@Getter
@Setter
public class BankAgreement extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 银行编码，即银行简称，例如工商银行ICBC
     */
    private String bankCode;

    /**
     * 商户编号，支付平台在银行的编号，由银行提供
     */
    private String merchantNo;

    /**
     * 合同编号，支付平台与银行签订的接口协议编号，由具体合同确定
     */
    private String agreementNo;

    /**
     * 银行序号，由支付平台生成用来区分银行接口的序号
     */
    private String bankSequence;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 大小额：银行区分系统使用大小额的金额限制
     */
    private Double amountSystem;

    /**
     * 1 B2B 2 B2C 3 快捷支付 4 批量大款 5 代收代付
     */
    private Integer linkType;

    /**
     * 通讯方式，HTTP、HTTPS、SFTP
     */
    private String communicationMode;

    /**
     * 通讯地址，支付平台请求银行的地址
     */
    private String communicationAddress;

    /**
     * 合同OA号，协议在支付平台OA系统中的标号
     */
    private String contractOANo;

    /**
     * 卡种，借记卡100、信用卡101
     */
    private String cardType;

    /**
     * 上线时间，协议正式生效时间
     */
    private Date onlineTime;

    /**
     * 下线时间，协议到期时间
     */
    private Date offlineTime;

    /**
     * 商户类型，接口支持的允许支付平台接入的商户类型
     */
    private String merchantType;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 银行联系人：名称、类型、电话、邮箱
     */
    private String linkMan;

    /**
     * 描述
     */
    private String remark;
}
