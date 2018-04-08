package com.bd.imooc.free.aop.dao;

import com.bd.imooc.free.aop.domain.Action;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionDAO extends MongoRepository<Action, String> {}
