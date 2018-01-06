package com.bd.roncoo.eshop.user.service.mapper;

import com.bd.roncoo.eshop.user.service.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> findAllUsers();

    @Select("SELECT * FROM user WHERE id=#{id}")
    User findUserInfo(Long id);
}
