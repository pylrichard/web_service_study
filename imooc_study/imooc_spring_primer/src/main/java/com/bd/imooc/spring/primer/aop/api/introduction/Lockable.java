package com.bd.imooc.spring.primer.aop.api.introduction;

public interface Lockable {
    void lock();

    void unlock();

    boolean isLocked();
}
