package com.bd.roncoo.book.shop.db.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

/**
 * SimpleJpaRepository实现了JpaRepository<T, ID>, JpaSpecificationExecutor<T>两个接口
 * Spring基于SimpleJpaRepository类为声明的Repository接口(BookRepository)生成代理对象
 */
public class BookShopRepositoryImpl<T> extends SimpleJpaRepository<T, Long> {
    public BookShopRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public <S extends T> S save(S entity) {
        System.out.println("saved: " + entity.getClass().getSimpleName());

        return super.save(entity);
    }
}
