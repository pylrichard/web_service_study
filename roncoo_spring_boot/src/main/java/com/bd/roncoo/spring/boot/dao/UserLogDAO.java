package com.bd.roncoo.spring.boot.dao;

import com.bd.roncoo.spring.boot.bean.UserLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogDAO extends JpaRepository<UserLog, Integer> {
    UserLog findByUserName(String string);

    Page<UserLog> findByUserName(String string, Pageable pageable);
}
