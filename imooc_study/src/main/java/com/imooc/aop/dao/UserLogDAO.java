package com.imooc.aop.dao;

import com.imooc.aop.domain.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogDAO extends JpaRepository<UserLog, Long> {}
