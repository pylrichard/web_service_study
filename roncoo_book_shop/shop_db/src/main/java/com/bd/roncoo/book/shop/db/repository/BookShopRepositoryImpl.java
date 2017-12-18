package com.bd.roncoo.book.shop.db.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * SimpleJpaRepository实现了JpaRepository<T, ID>, JpaSpecificationExecutor<T>两个接口
 * Spring基于SimpleJpaRepository类为声明的Repository接口(BookRepository)生成代理对象
 */
public class BookShopRepositoryImpl<T> extends SimpleJpaRepository<T, Long> {
    public BookShopRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    /**
     * SimpleJpaRepository有注解@Transactional(readOnly = true)
     * SimpleJpaRepository.save()单独声明注解@Transactional
     * 此处覆盖save()，需要声明注解@Transactional
     * <p>
     * Propagation.REQUIRED 当前调用链没有事务，创建新事务，如果已经有事务，则将方法调用放入当前事务，不创建新事务
     * Propagation.REQUIRES_NEW 无论当前调用链是否有事务，都创建新事务
     * 比如订单处理中下订单，下订单方法一旦被调用，创建一条日志，之后进行数据库操作，如果操作成功，日志标为成功，如果操作失败，日志标为失败
     * 无论操作成功/失败，日志创建必须成功，所以将日志创建放在一个新事务中
     */
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public <S extends T> S save(S entity) {
        System.out.println("saved: " + entity.getClass().getSimpleName());

        return super.save(entity);
    }
}
