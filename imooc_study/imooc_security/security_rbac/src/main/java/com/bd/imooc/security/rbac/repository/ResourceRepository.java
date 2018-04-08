package com.bd.imooc.security.rbac.repository;

import com.bd.imooc.security.rbac.domain.Resource;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends ImoocRepository<Resource> {
    Resource findByName(String name);
}
