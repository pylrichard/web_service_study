package com.imooc.aop.dao;

import com.imooc.aop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {}
