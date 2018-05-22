CREATE DATABASE imooc_sell;
USE imooc_sell;

CREATE TABLE `product_category` (
  `category_id`   BIGINT      NOT NULL AUTO_INCREMENT
  COMMENT '类目id',
  `category_name` VARCHAR(64) NOT NULL
  COMMENT '类目名称',
  `category_type` INT         NOT NULL
  COMMENT '类目编号',
  `create_time`   TIMESTAMP   NOT NULL DEFAULT current_timestamp
  COMMENT '创建时间',
  `update_time`   TIMESTAMP   NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
  COMMENT '修改时间',
  PRIMARY KEY (`category_id`),
  # 类目编号保持唯一
  UNIQUE KEY `u_category_type` (`category_type`)
)
  COMMENT '商品类目表';

CREATE TABLE `product_info` (
  # 使用VARCHAR做主键，可以避免数字类型上限问题
  `product_id`     BIGINT        NOT NULL AUTO_INCREMENT
  COMMENT '商品id',
  `product_name`   VARCHAR(64)   NOT NULL
  COMMENT '商品名称',
  `product_price`  DECIMAL(8, 2) NOT NULL
  COMMENT '商品单价',
  `product_stock`  INT           NOT NULL
  COMMENT '商品库存',
  `product_desc`   VARCHAR(64) COMMENT '商品描述',
  `product_icon`   VARCHAR(512) COMMENT '图片链接',
  `product_status` TINYINT(3)             DEFAULT '0'
  COMMENT '商品状态，0正常，1下架',
  `category_type`  INT           NOT NULL
  COMMENT '类目编号',
  # 5.6下2个字段设置DEFAULT current_timestamp会报错，推荐使用5.7
  `create_time`    TIMESTAMP     NOT NULL DEFAULT current_timestamp
  COMMENT '创建时间',
  # ON UPDATE在记录更新时同步更新时间
  `update_time`    TIMESTAMP     NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
  COMMENT '修改时间',
  PRIMARY KEY (`product_id`)
)
  COMMENT '商品表';

CREATE TABLE `order_info` (
  `order_id`      BIGINT        NOT NULL AUTO_INCREMENT
  COMMENT '订单id',
  `buyer_name`    VARCHAR(32)   NOT NULL
  COMMENT '买家名字',
  `buyer_phone`   VARCHAR(32)   NOT NULL
  COMMENT '买家电话',
  `buyer_address` VARCHAR(128)  NOT NULL
  COMMENT '买家地址',
  `buyer_openid`  VARCHAR(64)   NOT NULL
  COMMENT '买家微信OpenId',
  `order_amount`  DECIMAL(8, 2) NOT NULL
  COMMENT '订单总金额',
  `order_status`  TINYINT(3)    NOT NULL DEFAULT '0'
  COMMENT '订单状态，0新建，1已完结，2已取消',
  `pay_status`    TINYINT(3)    NOT NULL DEFAULT '0'
  COMMENT '支付状态，0未支付，1支付成功',
  `create_time`   TIMESTAMP     NOT NULL DEFAULT current_timestamp
  COMMENT '创建时间',
  `update_time`   TIMESTAMP     NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
  COMMENT '修改时间',
  PRIMARY KEY (`order_id`),
  KEY `i_buyer_openid` (`buyer_openid`)
)
  COMMENT '订单信息表';

CREATE TABLE `order_detail` (
  `detail_id`        BIGINT        NOT NULL AUTO_INCREMENT
  COMMENT '订单明细id',
  `order_id`         BIGINT        NOT NULL
  COMMENT '订单id',
  `product_id`       BIGINT        NOT NULL
  COMMENT '商品id',
  `product_name`     VARCHAR(64)   NOT NULL
  COMMENT '商品名称',
  `product_price`    DECIMAL(8, 2) NOT NULL
  COMMENT '商品单价',
  `product_quantity` INT           NOT NULL
  COMMENT '商品数量',
  `product_icon`     VARCHAR(512) COMMENT '小图',
  `create_time`      TIMESTAMP     NOT NULL DEFAULT current_timestamp
  COMMENT '创建时间',
  `update_time`      TIMESTAMP     NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
  COMMENT '修改时间',
  PRIMARY KEY (`detail_id`),
  KEY `i_order_id` (`order_id`)
)
  COMMENT '订单明细表';

CREATE TABLE `seller_info` (
  `seller_id`     BIGINT      NOT NULL AUTO_INCREMENT
  COMMENT '卖家id',
  `user_name`     VARCHAR(32) NOT NULL
  COMMENT '卖家用户名',
  `password`      VARCHAR(32) NOT NULL
  COMMENT '卖家密码',
  `seller_openid` VARCHAR(64) NOT NULL
  COMMENT '卖家微信OpenId',
  `create_time`   TIMESTAMP   NOT NULL DEFAULT current_timestamp
  COMMENT '创建时间',
  `update_time`   TIMESTAMP   NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
  COMMENT '修改时间',
  PRIMARY KEY (`seller_id`)
)
  COMMENT '卖家信息表';