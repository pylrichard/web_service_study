package com.bd.imooc.study.aop.dao;

import com.bd.imooc.study.aop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {}
