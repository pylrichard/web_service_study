SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for trade_coupon
-- ----------------------------
DROP TABLE IF EXISTS `trade_coupon`;
CREATE TABLE `trade_coupon` (
  `coupon_id` varchar(32) NOT NULL COMMENT '优惠券ID',
  `coupon_price` decimal(10,2) DEFAULT NULL COMMENT '优惠券金额',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `order_id` varchar(32) DEFAULT NULL COMMENT '订单ID',
  `is_used` char(1) DEFAULT NULL COMMENT '是否使用 0未使用 1已使用',
  `used_time` datetime DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of trade_coupon
-- ----------------------------

-- ----------------------------
-- Table structure for trade_goods
-- ----------------------------
DROP TABLE IF EXISTS `trade_goods`;
CREATE TABLE `trade_goods` (
  `goods_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `goods_number` int(11) DEFAULT NULL COMMENT '商品库存',
  `goods_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `goods_desc` varchar(255) DEFAULT NULL COMMENT '商品描述',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of trade_goods
-- ----------------------------

-- ----------------------------
-- Table structure for trade_goods_number_log
-- ----------------------------
DROP TABLE IF EXISTS `trade_goods_number_log`;
CREATE TABLE `trade_goods_number_log` (
  `goods_id` int(11) NOT NULL COMMENT '商品ID',
  `order_id` varchar(32) NOT NULL COMMENT '订单ID',
  `goods_number` int(11) DEFAULT NULL COMMENT '库存数量',
  `log_time` datetime DEFAULT NULL,
  PRIMARY KEY (`goods_id`,`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of trade_goods_number_log
-- ----------------------------

-- ----------------------------
-- Table structure for trade_order
-- ----------------------------
DROP TABLE IF EXISTS `trade_order`;
CREATE TABLE `trade_order` (
  `order_id` varchar(32) NOT NULL COMMENT '订单ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `order_status` char(1) DEFAULT NULL COMMENT '订单状态 0未确认 1已确认 2已取消 3无效 4退款',
  `pay_status` char(1) DEFAULT NULL COMMENT '支付状态 0未支付 1支付中 2已支付',
  `shipping_status` char(1) DEFAULT NULL COMMENT '发货状态 0未发货 1已发货 2已收货',
  `address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `consignee` varchar(255) DEFAULT NULL COMMENT '收货人',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `goods_number` int(11) DEFAULT NULL COMMENT '商品数量',
  `goods_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `goods_amount` decimal(10,0) DEFAULT NULL COMMENT '商品总价',
  `shipping_fee` decimal(10,2) DEFAULT NULL COMMENT '运费',
  `order_amount` decimal(10,2) DEFAULT NULL COMMENT '订单价格',
  `coupon_id` varchar(32) DEFAULT NULL COMMENT '优惠券ID',
  `coupon_paid` decimal(10,2) DEFAULT NULL COMMENT '优惠券金额',
  `money_paid` decimal(10,2) DEFAULT NULL COMMENT '已付金额',
  `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `confirm_time` datetime DEFAULT NULL COMMENT '订单确认时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of trade_order
-- ----------------------------

-- ----------------------------
-- Table structure for trade_pay
-- ----------------------------
DROP TABLE IF EXISTS `trade_pay`;
CREATE TABLE `trade_pay` (
  `pay_id` varchar(32) NOT NULL COMMENT '支付编号',
  `order_id` varchar(32) DEFAULT NULL COMMENT '订单编号',
  `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `is_paid` char(1) DEFAULT NULL COMMENT '是否已支付 0否 1是',
  PRIMARY KEY (`pay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of trade_pay
-- ----------------------------

-- ----------------------------
-- Table structure for trade_user
-- ----------------------------
DROP TABLE IF EXISTS `trade_user`;
CREATE TABLE `trade_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户姓名',
  `user_password` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `user_mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `user_score` int(11) DEFAULT NULL COMMENT '积分',
  `user_reg_time` datetime DEFAULT NULL COMMENT '注册时间',
  `user_money` decimal(10,0) DEFAULT NULL COMMENT '用户余额',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of trade_user
-- ----------------------------

-- ----------------------------
-- Table structure for trade_user_money_log
-- ----------------------------
DROP TABLE IF EXISTS `trade_user_money_log`;
CREATE TABLE `trade_user_money_log` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `order_id` varchar(32) NOT NULL COMMENT '订单ID',
  `money_log_type` char(1) NOT NULL COMMENT '日志类型 1订单付款 2 订单退款',
  `use_money` decimal(10,2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '日志时间',
  PRIMARY KEY (`user_id`,`order_id`,`money_log_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of trade_user_money_log
-- ----------------------------
