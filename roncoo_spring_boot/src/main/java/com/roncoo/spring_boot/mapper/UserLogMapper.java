package com.roncoo.spring_boot.mapper;

import com.roncoo.spring_boot.bean.UserLog;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface UserLogMapper {
    //回写自增的主键ID
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert(value = "insert into user_log (user_name, create_time, user_ip) values (#{userName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, #{userIp,jdbcType=VARCHAR})")
    int insert(UserLog record);

    @Select(value = "select id, user_name, create_time, user_ip from user_log where id = #{id,jdbcType=INTEGER}")
    @Results(value = { @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP) })
    UserLog selectByPrimaryKey(Integer id);
}
