package com.bd.imooc.study.aop.dao;

import com.bd.imooc.study.aop.domain.Action;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionDAO extends MongoRepository<Action, String> {}
