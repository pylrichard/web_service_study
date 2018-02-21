package com.bd.roncoo.pay.facade.bank.entity;

/**
 * 银行渠道参数实体
 */
public class BankChannel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 银行渠道编号：系统自动生成
     */
    private String bankChannelCode;

    /**
     * 银行渠道名称：系统自动生成
     */
    private String bankChannelName;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行编号：银行简称，例如工商银行ICBC
     */
    private BankCode bankCode;

    /**
     * 状态：100 激活 101 冻结
     */
    private Integer status;

    /**
     * 落地行名称：具体到支行
     */
    private String landingBankName;

    /**
     * 银行协议ID
     */
    private Long bankAgreementId;

    /**
     * 银行账户ID
     */
    private Long bankAccountId;

    /**
     * 描述
     */
    private String remark;

    /**
     * 银行序号，非数据表映射字段，用于展示数据
     */
    private String bankSequence;

    /**
     * 银行账户名称，非数据表映射字段，只用于展示数据
     */
    private String bankAccountName;

    private Double bankRateOrFee;

    private String province;

    private String city;
}
