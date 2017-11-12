package com.bd.imooc.study.aop.dao;

import com.bd.imooc.study.aop.domain.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopProductDAO extends JpaRepository<ShopProduct, Long> {
    ShopProduct findById(Long id);
}
