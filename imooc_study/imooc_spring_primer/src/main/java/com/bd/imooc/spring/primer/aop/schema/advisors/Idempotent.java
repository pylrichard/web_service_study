package com.bd.imooc.spring.primer.aop.schema.advisors;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    // marker annotation
}
