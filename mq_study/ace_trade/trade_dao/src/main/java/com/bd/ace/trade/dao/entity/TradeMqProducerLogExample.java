package com.bd.ace.trade.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradeMqProducerLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TradeMqProducerLogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNull() {
            addCriterion("group_name is null");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNotNull() {
            addCriterion("group_name is not null");
            return (Criteria) this;
        }

        public Criteria andGroupNameEqualTo(String value) {
            addCriterion("group_name =", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotEqualTo(String value) {
            addCriterion("group_name <>", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThan(String value) {
            addCriterion("group_name >", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("group_name >=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThan(String value) {
            addCriterion("group_name <", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThanOrEqualTo(String value) {
            addCriterion("group_name <=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLike(String value) {
            addCriterion("group_name like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotLike(String value) {
            addCriterion("group_name not like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameIn(List<String> values) {
            addCriterion("group_name in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotIn(List<String> values) {
            addCriterion("group_name not in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameBetween(String value1, String value2) {
            addCriterion("group_name between", value1, value2, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotBetween(String value1, String value2) {
            addCriterion("group_name not between", value1, value2, "groupName");
            return (Criteria) this;
        }

        public Criteria andMsgTopicIsNull() {
            addCriterion("msg_topic is null");
            return (Criteria) this;
        }

        public Criteria andMsgTopicIsNotNull() {
            addCriterion("msg_topic is not null");
            return (Criteria) this;
        }

        public Criteria andMsgTopicEqualTo(String value) {
            addCriterion("msg_topic =", value, "msgTopic");
            return (Criteria) this;
        }

        public Criteria andMsgTopicNotEqualTo(String value) {
            addCriterion("msg_topic <>", value, "msgTopic");
            return (Criteria) this;
        }

        public Criteria andMsgTopicGreaterThan(String value) {
            addCriterion("msg_topic >", value, "msgTopic");
            return (Criteria) this;
        }

        public Criteria andMsgTopicGreaterThanOrEqualTo(String value) {
            addCriterion("msg_topic >=", value, "msgTopic");
            return (Criteria) this;
        }

        public Criteria andMsgTopicLessThan(String value) {
            addCriterion("msg_topic <", value, "msgTopic");
            return (Criteria) this;
        }

        public Criteria andMsgTopicLessThanOrEqualTo(String value) {
            addCriterion("msg_topic <=", value, "msgTopic");
            return (Criteria) this;
        }

        public Criteria andMsgTopicLike(String value) {
            addCriterion("msg_topic like", value, "msgTopic");
            return (Criteria) this;
        }

        public Criteria andMsgTopicNotLike(String value) {
            addCriterion("msg_topic not like", value, "msgTopic");
            return (Criteria) this;
        }

        public Criteria andMsgTopicIn(List<String> values) {
            addCriterion("msg_topic in", values, "msgTopic");
            return (Criteria) this;
        }

        public Criteria andMsgTopicNotIn(List<String> values) {
            addCriterion("msg_topic not in", values, "msgTopic");
            return (Criteria) this;
        }

        public Criteria andMsgTopicBetween(String value1, String value2) {
            addCriterion("msg_topic between", value1, value2, "msgTopic");
            return (Criteria) this;
        }

        public Criteria andMsgTopicNotBetween(String value1, String value2) {
            addCriterion("msg_topic not between", value1, value2, "msgTopic");
            return (Criteria) this;
        }

        public Criteria andMsgTagIsNull() {
            addCriterion("msg_tag is null");
            return (Criteria) this;
        }

        public Criteria andMsgTagIsNotNull() {
            addCriterion("msg_tag is not null");
            return (Criteria) this;
        }

        public Criteria andMsgTagEqualTo(String value) {
            addCriterion("msg_tag =", value, "msgTag");
            return (Criteria) this;
        }

        public Criteria andMsgTagNotEqualTo(String value) {
            addCriterion("msg_tag <>", value, "msgTag");
            return (Criteria) this;
        }

        public Criteria andMsgTagGreaterThan(String value) {
            addCriterion("msg_tag >", value, "msgTag");
            return (Criteria) this;
        }

        public Criteria andMsgTagGreaterThanOrEqualTo(String value) {
            addCriterion("msg_tag >=", value, "msgTag");
            return (Criteria) this;
        }

        public Criteria andMsgTagLessThan(String value) {
            addCriterion("msg_tag <", value, "msgTag");
            return (Criteria) this;
        }

        public Criteria andMsgTagLessThanOrEqualTo(String value) {
            addCriterion("msg_tag <=", value, "msgTag");
            return (Criteria) this;
        }

        public Criteria andMsgTagLike(String value) {
            addCriterion("msg_tag like", value, "msgTag");
            return (Criteria) this;
        }

        public Criteria andMsgTagNotLike(String value) {
            addCriterion("msg_tag not like", value, "msgTag");
            return (Criteria) this;
        }

        public Criteria andMsgTagIn(List<String> values) {
            addCriterion("msg_tag in", values, "msgTag");
            return (Criteria) this;
        }

        public Criteria andMsgTagNotIn(List<String> values) {
            addCriterion("msg_tag not in", values, "msgTag");
            return (Criteria) this;
        }

        public Criteria andMsgTagBetween(String value1, String value2) {
            addCriterion("msg_tag between", value1, value2, "msgTag");
            return (Criteria) this;
        }

        public Criteria andMsgTagNotBetween(String value1, String value2) {
            addCriterion("msg_tag not between", value1, value2, "msgTag");
            return (Criteria) this;
        }

        public Criteria andMsgKeysIsNull() {
            addCriterion("msg_keys is null");
            return (Criteria) this;
        }

        public Criteria andMsgKeysIsNotNull() {
            addCriterion("msg_keys is not null");
            return (Criteria) this;
        }

        public Criteria andMsgKeysEqualTo(String value) {
            addCriterion("msg_keys =", value, "msgKeys");
            return (Criteria) this;
        }

        public Criteria andMsgKeysNotEqualTo(String value) {
            addCriterion("msg_keys <>", value, "msgKeys");
            return (Criteria) this;
        }

        public Criteria andMsgKeysGreaterThan(String value) {
            addCriterion("msg_keys >", value, "msgKeys");
            return (Criteria) this;
        }

        public Criteria andMsgKeysGreaterThanOrEqualTo(String value) {
            addCriterion("msg_keys >=", value, "msgKeys");
            return (Criteria) this;
        }

        public Criteria andMsgKeysLessThan(String value) {
            addCriterion("msg_keys <", value, "msgKeys");
            return (Criteria) this;
        }

        public Criteria andMsgKeysLessThanOrEqualTo(String value) {
            addCriterion("msg_keys <=", value, "msgKeys");
            return (Criteria) this;
        }

        public Criteria andMsgKeysLike(String value) {
            addCriterion("msg_keys like", value, "msgKeys");
            return (Criteria) this;
        }

        public Criteria andMsgKeysNotLike(String value) {
            addCriterion("msg_keys not like", value, "msgKeys");
            return (Criteria) this;
        }

        public Criteria andMsgKeysIn(List<String> values) {
            addCriterion("msg_keys in", values, "msgKeys");
            return (Criteria) this;
        }

        public Criteria andMsgKeysNotIn(List<String> values) {
            addCriterion("msg_keys not in", values, "msgKeys");
            return (Criteria) this;
        }

        public Criteria andMsgKeysBetween(String value1, String value2) {
            addCriterion("msg_keys between", value1, value2, "msgKeys");
            return (Criteria) this;
        }

        public Criteria andMsgKeysNotBetween(String value1, String value2) {
            addCriterion("msg_keys not between", value1, value2, "msgKeys");
            return (Criteria) this;
        }

        public Criteria andMsgBodyIsNull() {
            addCriterion("msg_body is null");
            return (Criteria) this;
        }

        public Criteria andMsgBodyIsNotNull() {
            addCriterion("msg_body is not null");
            return (Criteria) this;
        }

        public Criteria andMsgBodyEqualTo(String value) {
            addCriterion("msg_body =", value, "msgBody");
            return (Criteria) this;
        }

        public Criteria andMsgBodyNotEqualTo(String value) {
            addCriterion("msg_body <>", value, "msgBody");
            return (Criteria) this;
        }

        public Criteria andMsgBodyGreaterThan(String value) {
            addCriterion("msg_body >", value, "msgBody");
            return (Criteria) this;
        }

        public Criteria andMsgBodyGreaterThanOrEqualTo(String value) {
            addCriterion("msg_body >=", value, "msgBody");
            return (Criteria) this;
        }

        public Criteria andMsgBodyLessThan(String value) {
            addCriterion("msg_body <", value, "msgBody");
            return (Criteria) this;
        }

        public Criteria andMsgBodyLessThanOrEqualTo(String value) {
            addCriterion("msg_body <=", value, "msgBody");
            return (Criteria) this;
        }

        public Criteria andMsgBodyLike(String value) {
            addCriterion("msg_body like", value, "msgBody");
            return (Criteria) this;
        }

        public Criteria andMsgBodyNotLike(String value) {
            addCriterion("msg_body not like", value, "msgBody");
            return (Criteria) this;
        }

        public Criteria andMsgBodyIn(List<String> values) {
            addCriterion("msg_body in", values, "msgBody");
            return (Criteria) this;
        }

        public Criteria andMsgBodyNotIn(List<String> values) {
            addCriterion("msg_body not in", values, "msgBody");
            return (Criteria) this;
        }

        public Criteria andMsgBodyBetween(String value1, String value2) {
            addCriterion("msg_body between", value1, value2, "msgBody");
            return (Criteria) this;
        }

        public Criteria andMsgBodyNotBetween(String value1, String value2) {
            addCriterion("msg_body not between", value1, value2, "msgBody");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}