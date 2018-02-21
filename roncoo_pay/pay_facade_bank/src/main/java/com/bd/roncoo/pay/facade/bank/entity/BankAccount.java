package com.bd.roncoo.pay.facade.bank.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 银行账户信息表参数实体
 */
@Getter
@Setter
public class BankAccount extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 开户银行名称
     */
    private String openBank;

    /**
     * 开户支行地址
     */
    private String openBankAddress;

    /**
     * 账户开户日期
     */
    private Date opendate;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 银行支行行号，与银联系统数据相同
     */
    private String bankNo;

    /**
     * 银行账户名称
     */
    private String userName;

    /**
     * 开户经办人
     */
    private String operator;

    /**
     * 合作方式：1 存管银行 2 合作银行
     */
    private Integer cooperationWay;

    /**
     * 账户性质：1 备付金存管账户 2 自有资金账户 3 备付金收付账户 4 备付金汇缴账户
     */
    private Integer accountNature;

    /**
     * 账户状态：1 正常 2 待销户 3 已销户
     */
    private Integer accountStatus;

    /**
     * 账户类型：1 活期 2 定期 3 通支
     */
    private Integer accountType;

    /**
     * 网银管理费
     */
    private Double onlineBankFee;

    /**
     * 账户管理费
     */
    private Double accountMngFee;

    /**
     * 是否有网银：1 是 2 否
     */
    private Integer isOnlineBank;

    /**
     * 回单形式：1 邮寄 2 自取 3 打印
     */
    private Integer receiptForm;

    /**
     * 预留印鉴记录
     */
    private String reserveSeal;

    /**
     * 申请人
     */
    private String proposer;

    /**
     * 银行联系方式：姓名、类型、电话、邮箱 长文本存放
     */
    private String linkMan;

    /**
     * 开户信息预留人
     */
    private String openAccountObligate;

    /**
     * 网银验证码预留人
     */
    private String onlineBankObligate;

    /**
     * 大额转款确定预留人
     */
    private String bigAmounttransferObligate;

    /**
     * 质押保证金
     */
    private Double pledgefDeposits;

    /**
     * 备注
     */
    private String comments;

    /**
     * 初始化金额
     */
    private Double balance;
}
