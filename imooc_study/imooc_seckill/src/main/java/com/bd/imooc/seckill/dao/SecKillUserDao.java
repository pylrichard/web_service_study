package com.bd.imooc.seckill.dao;

import com.bd.imooc.seckill.domain.SecKillUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SecKillUserDao {
    @Select("select * from sec_kill_user where id = #{id}")
    SecKillUser getById(@Param("id") long id);

    @Update("update sec_kill_user set password = #{password} where id = #{id}")
    void update(SecKillUser toBeUpdate);
}
