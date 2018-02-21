package com.bd.roncoo.pay.facade.bank.entity;

/**
 * 银行结算信息参数实体
 */
public class BankSettlement extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 银行渠道编码
     */
    private String bankChannelCode;

    /**
     * 结算周期：T+X
     */
    private Integer settleCycle;

    /**
     * 手续费账户：关联银行账户表
     */
    private String chargeAccount;

    /**
     * 保证金账户：关联银行账户表
     */
    private String marginAccount;

    /**
     * 手续费扣收方式：1 内扣 2 外扣
     */
    private Integer chargeDeductWay;

    /**
     * 手续费扣收周期：1 实时 2 包年
     */
    private Integer chargeDeductCycle;

    /**
     * 手续费支付方式：1 自动扣帐 2 人工转账
     */
    private Integer chargePayWay;

    /**
     * 退款方式：1 内扣 2 外扣
     */
    private Integer refoundType;

    /**
     * 退款扣收方式：1 接口 2 网银 3 传真 4 邮件 5 邮寄
     */
    private Integer refoundDeductWay;

    /**
     * 退款有效期：X天内允许退款
     */
    private Integer refoundValidity;

    /**
     * 是否退回手续费：1 是 2 否
     */
    private Integer isReturnCharge;

    /**
     * 部分退款是否退回部分手续费：对于支持部分退款的情况 1 是 2 否
     */
    private Integer isReturnPartFee;

    /**
     * 退款到账时间 X天后到帐
     */
    private Integer refundAccountTime;

    /**
     * 退款限额
     */
    private Double refundLimit;

    /**
     * 是否非工作日到账 1 是 2 否
     */
    private Integer isNonWorkdayAccount;

    /**
     * 备注
     */
    private String comments;

    /**
     * 银行渠道名称，用来显示，对应数据库没有实际字段
     */
    private String bankChannelName;
}
