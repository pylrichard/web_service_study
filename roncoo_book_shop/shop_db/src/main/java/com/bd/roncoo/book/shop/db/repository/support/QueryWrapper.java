package com.bd.roncoo.book.shop.db.repository.support;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 包装用于构建JPA动态查询时所需的对象
 */
@Getter
@Setter
public class QueryWrapper<T> {
    public QueryWrapper(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, List<Predicate> predicates) {
        this.root = root;
        this.query = query;
        this.cb = cb;
        this.predicates = predicates;
    }

    private Root<T> root;
    private CriteriaBuilder cb;
    private List<Predicate> predicates;
    /**
     * JPA查询对象
     */
    private CriteriaQuery<?> query;

    public void addPredicate(Predicate predicate) {
        this.predicates.add(predicate);
    }
}
