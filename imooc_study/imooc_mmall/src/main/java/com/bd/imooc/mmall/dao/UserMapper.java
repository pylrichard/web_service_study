package com.bd.imooc.mmall.dao;

import com.bd.imooc.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUserName(String userName);

    User findUserInfo(@Param("userName") String userName, @Param("password")String password);

    int checkEmail(String email);

    String findPasswordQuestion(String userName);

    int checkPasswordAnswer(@Param("userName")String userName, @Param("question")String question,
                            @Param("answer")String answer);

    int updatePassword(@Param("userName")String userName, @Param("newPassword")String newPassword);

    int checkPassword(@Param("password")String password, @Param("userId")Integer userId);

    int checkEmail(@Param("email")String email, @Param("userId")Integer userId);
}