package com.bd.imooc.free.aop.dao;

import com.bd.imooc.free.aop.domain.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopProductDAO extends JpaRepository<ShopProduct, Long> {
    ShopProduct findById(Long id);
}
