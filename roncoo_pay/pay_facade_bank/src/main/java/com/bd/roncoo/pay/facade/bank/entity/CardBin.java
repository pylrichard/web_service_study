package com.bd.roncoo.pay.facade.bank.entity;

import java.util.Date;

/**
 * 银行卡CardBIN参数实体
 */
public class CardBin extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 卡BIN
     */
    private String cardBin;

    /**
     * 发卡行代码
     */
    private String bankCode;

    /**
     * 发卡行名称
     */
    private String bankName;

    /**
     * 卡名
     */
    private String cardName;

    /**
     * 卡种 1 借记卡 2 贷记卡 3 准贷记卡 4 预付费卡
     */
    private Integer cardKind;

    /**
     * 卡片长度
     */
    private Integer cardLength;

    /**
     * 状态 101 无效 100 有效
     */
    private Integer status;

    /**
     * 最后修改人
     */
    private Long lastUpdatorId;

    /**
     * 最后修改时间
     */
    private Date lastUpdateTime;

    /**
     * 最后修改人姓名
     */
    private String lastUpdatorName;
}
