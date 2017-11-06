package com.imooc.mmall.dao;

import com.imooc.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String userName);

    User findUserInfo(@Param("user_name") String userName, @Param("password")String password);

    int checkEmail(String email);

    String findPasswordQuestion(String userName);

    int checkPasswordAnswer(@Param("user_name")String userName, @Param("question")String question,
                            @Param("answer")String answer);
}