package com.bd.imooc.security.rbac.repository.support;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public abstract class ImoocSpecification<T, C> extends AbstractEventConditionBuilder<T, C>
        implements Specification<T> {
    public ImoocSpecification(C condition) {
        super(condition);
    }

    /**
     * 构建查询条件，子类必须实现addCondition方法来编写查询的逻辑
     * <p>
     * 子类可以通过addFetch方法控制查询的关联和抓取行为
     */
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (Long.class != query.getResultType()) {
            addFetch(root);
        }
        List<Predicate> predicates = new ArrayList<Predicate>();
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>(root, query, cb, predicates);
        addCondition(queryWrapper);
        Predicate permissionCondition = getPermissionCondition(queryWrapper);
        if (permissionCondition != null) {
            queryWrapper.addPredicate(permissionCondition);
        }

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    /**
     * 添加权限条件，如果要查询的domain实现了ManagedByOrgan接口，那么传入的Condition对象也应该实现ManagedByOrgan接口
     * 程序会尝试从Condition对象获取organFullId，然后作为like查询条件添加到查询中
     * 查出所有以传入的organFullId开头的domain
     */
    protected Predicate getPermissionCondition(QueryWrapper<T> queryWrapper) {
        return null;
    }

    /**
     * 子类可以通过覆盖此方法实现关联抓取，避免n+1查询
     */
    protected void addFetch(Root<T> root) {
    }

    protected abstract void addCondition(QueryWrapper<T> queryWrapper);
}
