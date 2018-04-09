package com.bd.roncoo.spring.boot.primer.mapper;

import com.bd.roncoo.spring.boot.primer.bean.UserLog;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface UserLogMapper {
    //回写自增的主键ID
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert(value = "insert into user_log (user_name, create_time, user_ip) values (#{userName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, #{userIp,jdbcType=VARCHAR})")
    int insert(UserLog record);

    //如果表字段为SQL关键字，如update，在SQL语句中要写为`update`
    @Select(value = "select id, user_name, create_time, user_ip from user_log where id = #{id,jdbcType=INTEGER}")
    //property为Java类成员变量，column为DB表字段
    @Results(value = {
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP)
    })
    UserLog selectByPrimaryKey(Integer id);
}
