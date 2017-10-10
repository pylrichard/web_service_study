package com.roncoo.spring_boot.cache;

import com.roncoo.spring_boot.bean.UserLog;

public interface UserLogCache {
    UserLog selectById(Integer id);

    UserLog updateById(UserLog roncooUserLog);

    void deleteById(Integer id);
}