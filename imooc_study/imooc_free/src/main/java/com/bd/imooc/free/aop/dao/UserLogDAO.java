package com.bd.imooc.free.aop.dao;

import com.bd.imooc.free.aop.domain.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogDAO extends JpaRepository<UserLog, Long> {}
