# README

## 项目启动注意事项
1、数据库配置 /resources/settings.properties
2、Redis配置 /resources/redis.properties
3、项目登录页 /signin.jsp
4、登录使用用户名和密码
username: admin@qq.com
password: 12345678

## 其他
1、不想使用Redis，如何移除
1) applicationContext.xml里移除<import resource="redis.xml"/>
2) 修改RedisPool.java类取消被Spring管理
3) 修改SysCacheService.java类移除RedisPool.java的使用

2、生产环境使用注意事项
1) 集群部署需要配置session共享，保证登录一次
参考http://blog.csdn.net/pingnanlee/article/details/68065535
2) 确认管理员的权限规则
SysCoreService.java.isSuperAdmin()
3) 新增管理员的密码处理
SysUserService.save()
补全MailUtil邮件发送者参数，在SysUserService.save()的sysUserMapper.insertSelective(user)之前调用