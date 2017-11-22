package com.bd.imooc.security.core.social.qq.api;

public class QQUserInfo {
    /**
     * 返回码
     */
    private String ret;
    /**
     * 如果ret < 0，会有相应的错误信息提示，返回数据全部用UTF-8编码
     */
    private String msg;
    private String openId;
    private String isLost;
    /**
     * 省(直辖市)
     */
    private String province;
    /**
     * 市(直辖市区)
     */
    private String city;
    /**
     * 出生年月
     */
    private String year;
    /**
     * QQ空间的昵称
     */
    private String nickname;
    /**
     * 大小为30×30像素的QQ空间头像URL
     */
    private String figureUrl;
    /**
     * 大小为50×50像素的QQ空间头像URL
     */
    private String figureUrl1;
    /**
     * 大小为100×100像素的QQ空间头像URL
     */
    private String figureUrl2;
    /**
     * 大小为40×40像素的QQ头像URL
     */
    private String figureUrlQQ1;
    /**
     * 大小为100×100像素的QQ头像URL。注意不是所有用户都拥有100×100的头像，但40×40像素一定会有
     */
    private String figureUrlQQ2;
    /**
     * 性别。 如果获取不到则默认返回男
     */
    private String gender;
    /**
     * 标识用户是否为黄钻用户(0：不是，1：是)
     */
    private String isYellowVip;
    /**
     * 标识用户是否为VIP用户(0：不是，1：是)
     */
    private String vip;
    /**
     * 黄钻等级
     */
    private String YellowVipLevel;
    /**
     * VIP等级
     */
    private String level;
    /**
     * 标识是否为年费黄钻用户(0：不是，1：是)
     */
    private String isYellowYearVip;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIsLost() {
        return isLost;
    }

    public void setIsLost(String isLost) {
        this.isLost = isLost;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFigureUrl() {
        return figureUrl;
    }

    public void setFigureUrl(String figureUrl) {
        this.figureUrl = figureUrl;
    }

    public String getFigureUrl1() {
        return figureUrl1;
    }

    public void setFigureUrl1(String figureUrl1) {
        this.figureUrl1 = figureUrl1;
    }

    public String getFigureUrl2() {
        return figureUrl2;
    }

    public void setFigureUrl2(String figureUrl2) {
        this.figureUrl2 = figureUrl2;
    }

    public String getFigureUrlQQ1() {
        return figureUrlQQ1;
    }

    public void setFigureUrlQQ1(String figureUrlQQ1) {
        this.figureUrlQQ1 = figureUrlQQ1;
    }

    public String getFigureUrlQQ2() {
        return figureUrlQQ2;
    }

    public void setFigureUrlQQ2(String figureUrlQQ2) {
        this.figureUrlQQ2 = figureUrlQQ2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIsYellowVip() {
        return isYellowVip;
    }

    public void setIsYellowVip(String isYellowVip) {
        this.isYellowVip = isYellowVip;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getYellowVipLevel() {
        return YellowVipLevel;
    }

    public void setYellowVipLevel(String yellowVipLevel) {
        YellowVipLevel = yellowVipLevel;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIsYellowYearVip() {
        return isYellowYearVip;
    }

    public void setIsYellowYearVip(String isYellowYearVip) {
        this.isYellowYearVip = isYellowYearVip;
    }
}
