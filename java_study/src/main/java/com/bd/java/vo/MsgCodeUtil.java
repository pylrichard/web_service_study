package com.bd.java.vo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 获取消息工具类
 */
public class MsgCodeUtil {
    private static Properties properties;

    static {
        properties = new Properties();

        /*
            code_msg.properties中的数据格式为
            global.param.is.null=code:msg
            global.systemError=code:msg
         */
        try (InputStreamReader reader = new InputStreamReader(
                MsgCodeUtil.class.getClassLoader().getResourceAsStream("code_msg.properties"), "utf-8")) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过key获取消息
     */
    public static String getMsg(String key) {
        if (key == null) {
            return key;
        }
        return properties.getProperty(key).split(":").length == 2 ? properties.getProperty(key).split(":")[1] : null;
    }

    /**
     * 通过key获取消息并添加扩展内容
     */
    public static String getMsg(String key, String extMsg) {
        if (key == null) {
            return key;
        }
        return properties.getProperty(key).split(":")[1] + "." + extMsg;
    }

    /**
     * 通过key获取code
     */
    public static String getMsgCode(String key) {
        if (key == null) {
            return key;
        }
        return properties.getProperty(key).split(":")[0];
    }
}
