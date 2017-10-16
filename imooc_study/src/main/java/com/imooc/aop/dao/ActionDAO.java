package com.imooc.aop.dao;

import com.imooc.aop.domain.Action;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionDAO extends MongoRepository<Action, String> {}
