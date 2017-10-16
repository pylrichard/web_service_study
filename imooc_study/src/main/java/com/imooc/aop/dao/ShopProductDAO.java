package com.imooc.aop.dao;

import com.imooc.aop.domain.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopProductDAO extends JpaRepository<ShopProduct, Long> {
    ShopProduct findById(Long id);
}
