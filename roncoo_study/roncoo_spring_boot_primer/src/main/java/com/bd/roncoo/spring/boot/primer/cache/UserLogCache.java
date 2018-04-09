package com.bd.roncoo.spring.boot.primer.cache;

import com.bd.roncoo.spring.boot.primer.bean.UserLog;

public interface UserLogCache {
    UserLog selectById(Integer id);

    UserLog updateById(UserLog roncooUserLog);

    void deleteById(Integer id);
}