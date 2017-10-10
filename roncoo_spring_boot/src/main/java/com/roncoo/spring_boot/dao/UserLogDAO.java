package com.roncoo.spring_boot.dao;

import com.roncoo.spring_boot.bean.UserLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogDAO extends JpaRepository<UserLog, Integer> {
    UserLog findByUserName(String string);

    Page<UserLog> findByUserName(String string, Pageable pageable);
}
