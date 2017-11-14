package com.bd.ace.trade.common.constant;

public class TradeEnums {
    public enum RestServerEnum {
        ORDER("localhost", "order", 8080),
        PAY("localhost", "pay", 8081),
        COUPON("localhost", "coupon", 8082),
        GOODS("localhost", "goods", 8083),
        USER("localhost", "user", 8084);

        private int serverPort;
        private String serverHost;
        private String contextPath;

        RestServerEnum(String serverHost, String contextPath, int serverPort) {
            this.serverPort = serverPort;
            this.serverHost = serverHost;
            this.contextPath = contextPath;
        }

        public int getServerPort() {
            return serverPort;
        }

        public void setServerPort(int serverPort) {
            this.serverPort = serverPort;
        }

        public String getServerHost() {
            return serverHost;
        }

        public void setServerHost(String serverHost) {
            this.serverHost = serverHost;
        }

        public String getContextPath() {
            return contextPath;
        }

        public void setContextPath(String contextPath) {
            this.contextPath = contextPath;
        }

        public String getServerUrl() {
            return "http://" + this.serverHost + ":" + this.serverPort + "/" + this.contextPath + "/";
        }

    }

    public enum RetEnum {
        SUCCESS("1", "成功"), FAIL("-1", "失败");

        private String code;
        private String desc;

        RetEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

    }

    public enum OrderStatusEnum {
        NO_CONFIRM("0", "未确认"), CONFIRM("1", "已确认"), CANCEL("2", "已取消"),
        INVALID("3", "无效"), RETURNED("4", "退货");

        private String statusCode;
        private String desc;

        OrderStatusEnum(String statusCode, String desc) {
            this.statusCode = statusCode;
            this.desc = desc;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum PayStatusEnum {
        NO_PAY("0", "未付款"), PAYING("1", "支付中"), PAID("2", "已付款");

        private String statusCode;
        private String desc;

        PayStatusEnum(String statusCode, String desc) {
            this.statusCode = statusCode;
            this.desc = desc;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum ShippingStatusEnum {
        NO_SHIP("0", "未发货"), SHIPPED("1", "已发货"), RECEIVED("2", "已收货");

        private String statusCode;
        private String desc;

        ShippingStatusEnum(String statusCode, String desc) {
            this.statusCode = statusCode;
            this.desc = desc;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum YesNoEnum {
        YES("0", "是"), NO("1", "否");

        private String code;
        private String desc;

        YesNoEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum UserMoneyLogTypeEnum {
        PAID("1", "订单付款"), REFUND("2", "订单退款");

        private String code;
        private String desc;

        UserMoneyLogTypeEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
