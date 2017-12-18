package com.bd.roncoo.book.shop.db.repository.specification;

import com.bd.roncoo.book.shop.common.dto.AuthorCondition;
import com.bd.roncoo.book.shop.db.domain.Author;
import com.bd.roncoo.book.shop.db.repository.support.QueryWrapper;
import org.apache.commons.lang.StringUtils;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AuthorSpecification extends BookShopSpecification<Author, AuthorCondition> {
    public AuthorSpecification(AuthorCondition condition) {
        super(condition);
    }

    @Override
    protected void addCondition(QueryWrapper<Author> queryWrapper) {
        /*
            构建or查询条件
         */
        String name = getCondition().getName();
        String email = getCondition().getEmail();
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(email)) {
            Predicate nameLike = createLikeCondition(queryWrapper, "name", name);
            Predicate emailLike = createLikeCondition(queryWrapper, "email", email);
            queryWrapper.addPredicate(queryWrapper.getCb().or(nameLike, emailLike));
        }
        addBetweenCondition(queryWrapper, "age");
        addEqualsCondition(queryWrapper, "sex");
    }

    @Override
    protected void addFetch(Root<Author> root) {
        /*
            关联bs_book_author表
         */
        root.fetch("books", JoinType.LEFT);
    }
}
