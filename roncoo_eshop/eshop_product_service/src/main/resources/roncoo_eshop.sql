USE `roncoo_eshop`;

/*Table structure for table `brand` */

CREATE TABLE `brand` (
  `id`          INT(11) NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(255)     DEFAULT NULL,
  `description` VARCHAR(255)     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;

/*Data for the table `brand` */

INSERT INTO `brand` (`id`, `name`, `description`) VALUES (1, '三星', '韩国品牌22');
INSERT INTO `brand` (`id`, `name`, `description`) VALUES (2, 'iPhone', '美国品牌99');

/*Table structure for table `category` */

CREATE TABLE `category` (
  `id`          INT(11) NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(255)     DEFAULT NULL,
  `description` VARCHAR(255)     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8;

/*Data for the table `category` */

INSERT INTO `category` (`id`, `name`, `description`) VALUES (1, '平板电脑', '电子产品');
INSERT INTO `category` (`id`, `name`, `description`) VALUES (2, '手机', '电子类');
INSERT INTO `category` (`id`, `name`, `description`) VALUES (4, '汽车', '出行类');
INSERT INTO `category` (`id`, `name`, `description`) VALUES (5, '笔记本电脑', '电子产品');
INSERT INTO `category` (`id`, `name`, `description`) VALUES (6, 'iPhone', '热门手机分类');

/*Table structure for table `product` */

CREATE TABLE `product` (
  `id`          INT(11) NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(255)     DEFAULT NULL,
  `category_id` INT(11)          DEFAULT NULL,
  `brand_id`    INT(11)          DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

/*Data for the table `product` */

INSERT INTO `product` (`id`, `name`, `category_id`, `brand_id`)
VALUES (1, 'Apple/苹果 iPhone 7 中国红亮黑色磨砂黑 国行全网通 港版未激活', 6, 2);

/*Table structure for table `product_intro` */

CREATE TABLE `product_intro` (
  `id`         INT(11) NOT NULL AUTO_INCREMENT,
  `content`    VARCHAR(255)     DEFAULT NULL,
  `product_id` INT(11)          DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

/*Data for the table `product_intro` */

INSERT INTO `product_intro` (`id`, `content`, `product_id`) VALUES (1, '1.jpg,2.jpg,3.jpg,4.jpg,5.jpg,6.jpg,7.png', 1);

/*Table structure for table `product_inventory` */

CREATE TABLE `product_inventory` (
  `id`            INT(11) NOT NULL AUTO_INCREMENT,
  `inventory_cnt` INT(11)          DEFAULT NULL,
  `product_id`    INT(11)          DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

/*Data for the table `product_inventory` */

INSERT INTO `product_inventory` (`id`, `inventory_cnt`, `product_id`) VALUES (1, 755, 1);

/*Table structure for table `product_price` */

CREATE TABLE `product_price` (
  `id`         INT(11) NOT NULL AUTO_INCREMENT,
  `value`      DECIMAL(11, 2)   DEFAULT NULL,
  `product_id` INT(11)          DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

/*Data for the table `product_price` */

INSERT INTO `product_price` (`id`, `value`, `product_id`) VALUES (1, '7480.00', 1);

/*Table structure for table `product_property` */

CREATE TABLE `product_property` (
  `id`         INT(11) NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(255)     DEFAULT NULL,
  `value`      VARCHAR(255)     DEFAULT NULL,
  `product_id` INT(11)          DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

/*Data for the table `product_property` */

INSERT INTO `product_property` (`id`, `name`, `value`, `product_id`)
VALUES (1, 'iPhone手机的属性', '\"机身颜色=金色,银色;存储容量=32GB,128GB;版本类型=港澳台,中国大陆\"', 1);

/*Table structure for table `product_specification` */

CREATE TABLE `product_specification` (
  `id`         INT(11) NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(255)     DEFAULT NULL,
  `value`      VARCHAR(255)     DEFAULT NULL,
  `product_id` INT(11)          DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

/*Data for the table `product_specification` */

INSERT INTO `product_specification` (`id`, `name`, `value`, `product_id`)
VALUES (1, 'iPhone手机的规格', '上市时间=2017-03,机身厚度=0.7cm,售后保障=五星服务', 1);

/*Table structure for table `user` */

CREATE TABLE `user` (
  `id`   INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255)     DEFAULT NULL,
  `age`  INT(11)          DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/*Data for the table `user` */
