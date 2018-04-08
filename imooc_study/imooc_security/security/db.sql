-- 记住我功能用的表
create table persistent_logins (username varchar(64) not null,
								series varchar(64) primary key,
								token varchar(64) not null,
								last_used timestamp not null);
-- 社交登录用的表，存放业务系统用户与第三方应用用户的关联关系
CREATE TABLE imooc_UserConnection (
	-- 业务系统的用户ID
	userId         VARCHAR(255) NOT NULL,
	providerId varchar(255) not null,
	providerUserId varchar(255),
	rank int not null,
	displayName varchar(255),
	profileUrl varchar(512),
	imageUrl varchar(512),
	accessToken varchar(512) not null,
	secret varchar(512),
	refreshToken varchar(512),
	expireTime bigint,
	primary key (userId, providerId, providerUserId));
create unique index UserConnectionRank on imooc_UserConnection(userId, providerId, rank);