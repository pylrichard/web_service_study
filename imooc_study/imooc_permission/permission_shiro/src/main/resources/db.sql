-- 权限表 --
CREATE TABLE permission (
  pid  INT(11)      NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL DEFAULT '',
  url  VARCHAR(255)          DEFAULT '',
  PRIMARY KEY (pid)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO permission VALUES ('1', 'add', '');
INSERT INTO permission VALUES ('2', 'delete', '');
INSERT INTO permission VALUES ('3', 'edit', '');
INSERT INTO permission VALUES ('4', 'query', '');

-- 用户表 --
CREATE TABLE user (
  uid       INT(11)      NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(255) NOT NULL DEFAULT '',
  password  VARCHAR(255) NOT NULL DEFAULT '',
  PRIMARY KEY (uid)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO user VALUES ('1', 'admin', '123');
INSERT INTO user VALUES ('2', 'customer', '123');

-- 角色表 --
CREATE TABLE role (
  rid   INT(11)      NOT NULL AUTO_INCREMENT,
  rname VARCHAR(255) NOT NULL DEFAULT '',
  PRIMARY KEY (rid)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO role VALUES ('1', 'admin');
INSERT INTO role VALUES ('2', 'customer');

-- 角色权限关系表 --
CREATE TABLE permission_role (
  rid INT(11) NOT NULL,
  pid INT(11) NOT NULL,
  KEY idx_rid (rid),
  KEY idx_pid (pid)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO permission_role VALUES ('1', '1');
INSERT INTO permission_role VALUES ('1', '2');
INSERT INTO permission_role VALUES ('1', '3');
INSERT INTO permission_role VALUES ('1', '4');
INSERT INTO permission_role VALUES ('2', '1');
INSERT INTO permission_role VALUES ('2', '4');

-- 用户角色关系表 --
CREATE TABLE user_role (
  uid INT(11) NOT NULL,
  rid INT(11) NOT NULL,
  KEY idx_uid (uid),
  KEY idx_rid (rid)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO user_role VALUES (1, 1);
INSERT INTO user_role VALUES (2, 2);