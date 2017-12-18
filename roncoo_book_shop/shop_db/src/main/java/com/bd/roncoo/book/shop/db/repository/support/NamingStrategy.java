package com.bd.roncoo.book.shop.db.repository.support;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.spi.MetadataBuildingContext;

public class NamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {
    private static final long serialVersionUID = -8100311517280290465L;

    /**
     * 生成表名和字段名
     */
    @Override
    protected Identifier toIdentifier(String stringForm, MetadataBuildingContext buildingContext) {
        /*
            stringForm是表名和字段名
            在application.properties中添加spring.jpa.hibernate.naming.implicit-strategy=xxx.support.NamingStrategy
        */
        return super.toIdentifier("bs_" + stringForm, buildingContext);
    }
}
