package com.bd.imooc.free.aop.dao;

import com.bd.imooc.free.aop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {}
