package com.bd.imooc.permission.shiro.mapper;

import com.bd.imooc.permission.shiro.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findByUserName(@Param("user_name") String userName);
}
