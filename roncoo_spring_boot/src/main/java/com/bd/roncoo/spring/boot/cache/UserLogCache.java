package com.bd.roncoo.spring.boot.cache;

import com.bd.roncoo.spring.boot.bean.UserLog;

public interface UserLogCache {
    UserLog selectById(Integer id);

    UserLog updateById(UserLog roncooUserLog);

    void deleteById(Integer id);
}