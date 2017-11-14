package com.bd.ace.trade.common.constant;

public class MqEnums {
    public enum TopicEnum {
        ORDER_CONFIRM("orderTopic", "confirm"), ORDER_CANCEL("orderTopic", "cancel"), PAY_PAID("payTopic", "paid");
        private String topic;
        private String tag;

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        TopicEnum(String topic, String tag) {
            this.topic = topic;
            this.tag = tag;
        }

    }

    public enum ConsumerStatusEnum {
        PROCESSING("0", "正在处理"), SUCCESS("1", "处理成功"), FAIL("2", "处理失败");
        private String statusCode;
        private String desc;

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

        ConsumerStatusEnum(String statusCode, String desc) {
            this.statusCode = statusCode;
            this.desc = desc;
        }
    }
}
