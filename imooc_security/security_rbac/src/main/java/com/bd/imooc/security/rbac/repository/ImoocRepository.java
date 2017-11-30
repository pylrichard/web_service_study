package com.bd.imooc.security.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ImoocRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
