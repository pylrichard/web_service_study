package com.bd.imooc.security.core.properties;

/**
 * 图片验证码配置项
 */
public class ImageCodeProperties extends SmsCodeProperties {
    public ImageCodeProperties() {
        //设置随机数为4位
        setLength(4);
    }

    private int width = 67;
    private int height = 23;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
