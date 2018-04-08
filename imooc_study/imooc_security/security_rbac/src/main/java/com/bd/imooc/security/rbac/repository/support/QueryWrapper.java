package com.bd.imooc.security.rbac.repository.support;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 包装用于构建JPA动态查询时所需的对象
 */
public class QueryWrapper<T> {

    /**
     * @param root       JPA Root
     * @param cb         JPA CriteriaBuilder
     * @param predicates JPA Predicate集合
     */
    public QueryWrapper(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, List<Predicate> predicates) {
        this.root = root;
        this.query = query;
        this.cb = cb;
        this.predicates = predicates;
    }

    /**
     * JPA Root
     */
    private Root<T> root;
    /**
     * JPA CriteriaBuilder
     */
    private CriteriaBuilder cb;
    /**
     * JPA Predicate集合
     */
    private List<Predicate> predicates;
    /**
     * JPA查询对象
     */
    private CriteriaQuery<?> query;

    public void addPredicate(Predicate predicate) {
        this.predicates.add(predicate);
    }

    public Root<T> getRoot() {
        return root;
    }

    public void setRoot(Root<T> root) {
        this.root = root;
    }

    public CriteriaBuilder getCb() {
        return cb;
    }

    public void setCb(CriteriaBuilder cb) {
        this.cb = cb;
    }

    public List<Predicate> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<Predicate> predicates) {
        this.predicates = predicates;
    }

    public CriteriaQuery<?> getQuery() {
        return query;
    }

    public void setQuery(CriteriaQuery<?> query) {
        this.query = query;
    }
}
