package com.bd.java.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
@Getter
@Setter
@NoArgsConstructor
public class BaseVO<T extends BaseVO> implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1892029806696959264L;
    /**
     * 成功标识，默认true
     */
    protected boolean isSuccessful = true;
    /**
     * 消息代码
     */
    protected String code;
    /**
     * 消息内容
     */
    protected String msg;

    /**
     * 创建消息
     *
     * @param isSuccessful 成功标识，前端根据此标识判断请求是否成功
     * @param code         消息代码
     * @param msg          消息内容，前端根据需要获取内容，后端根据需要返回
     */
    @SuppressWarnings({"unchecked"})
    public static BaseVO createMsg(boolean isSuccessful, String code, String msg) {
        BaseVO vo = new BaseVO();
        vo.isSuccessful = isSuccessful;
        vo.msg = msg;
        vo.code = code;

        return vo;
    }

    /**
     * 通过key创建消息
     *
     * @param key 属性文件key
     */
    @SuppressWarnings("unchecked")
    public static BaseVO createMsgByKey(String key, boolean isSuccessful) {
        BaseVO vo = new BaseVO();
        vo.isSuccessful = isSuccessful;
        vo.code = MsgCodeUtil.getMsgCode(key);
        vo.msg = MsgCodeUtil.getMsg(key);

        return vo;
    }

    /**
     * 通过key创建错误消息
     */
    public static BaseVO createErrorMsgByKey(String key) {
        return createMsgByKey(key, false);
    }

    /**
     * 通过key创建成功消息
     *
     * @param key 属性文件key
     * @return BaseVO
     */
    public static BaseVO createSuccessMsgByKey(String key) {
        return createMsgByKey(key, true);
    }

    /**
     * 创建code为空的成功消息
     */
    public static BaseVO createSuccessMsgWithEmptyCode(String msg) {
        return createMsg(true, null, msg);
    }

    /**
     * 创建code为空的错误消息
     */
    public static BaseVO createErrorMsgWithEmptyCode(String msg) {
        return createMsg(false, null, msg);
    }

    /**
     * 创建返回值为泛型的消息
     */
    @SuppressWarnings("unchecked")
    public T createMsgReturnT(boolean isSuccessful, String code, String msg) {
        this.isSuccessful = isSuccessful;
        this.code = code;
        this.msg = msg;

        return (T) this;
    }

    /**
     * 通过key创建返回值为泛型的消息
     */
    @SuppressWarnings("unchecked")
    public T createMsgByKeyReturnT(String key, boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
        this.code = MsgCodeUtil.getMsgCode(key);
        this.msg = MsgCodeUtil.getMsg(key);

        return (T) this;
    }

    /**
     * 通过key创建返回值为泛型的错误消息
     */
    public T createErrorMsgByKeyyReturnT(String key) {
        return createMsgByKeyReturnT(key, false);
    }

    /**
     * 通过key创建返回值为泛型的成功消息
     */
    public T createSuccessMsgByKeyReturnT(String key) {
        return createMsgByKeyReturnT(key, true);
    }

    /**
     * 创建code为空返回值为泛型的错误消息
     */
    public T createErrorMsgReturnT(String message) {
        return createMsgReturnT(false, null, message);
    }

    /**
     * 创建code为空返回值为泛型的成功消息
     */
    public T createSuccessMsgReturnT(String message) {
        return createMsgReturnT(true, null, message);
    }

    /**
     * 构造函数
     */
    public BaseVO(boolean isSuccessful, String key, String msg) {
        this.isSuccessful = isSuccessful;
        if (StringUtils.isNotBlank(key)) {
            this.code = MsgCodeUtil.getMsgCode(key);
            this.msg = MsgCodeUtil.getMsg(key);
        }
        if (StringUtils.isNotBlank(msg)) {
            this.msg = msg;
        }
    }
}
