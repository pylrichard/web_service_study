package com.bd.roncoo.eshop.user.service.mapper;

import com.bd.roncoo.eshop.user.service.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> findAllUsers();
}
