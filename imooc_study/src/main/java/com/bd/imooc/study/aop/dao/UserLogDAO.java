package com.bd.imooc.study.aop.dao;

import com.bd.imooc.study.aop.domain.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogDAO extends JpaRepository<UserLog, Long> {}
