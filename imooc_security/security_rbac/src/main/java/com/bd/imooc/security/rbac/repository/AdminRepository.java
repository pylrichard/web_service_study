package com.bd.imooc.security.rbac.repository;

import com.bd.imooc.security.rbac.domain.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends ImoocRepository<Admin> {
    Admin findByUsername(String username);
}
