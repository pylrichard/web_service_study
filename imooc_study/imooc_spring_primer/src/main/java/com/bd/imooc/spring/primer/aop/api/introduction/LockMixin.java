package com.bd.imooc.spring.primer.aop.api.introduction;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class LockMixin extends DelegatingIntroductionInterceptor implements Lockable {
    private static final long serialVersionUID = 6943163819932660450L;

    private boolean locked;

    @Override
    public void lock() {
        this.locked = true;
    }

    @Override
    public void unlock() {
        this.locked = false;
    }

    @Override
    public boolean isLocked() {
        return this.locked;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (isLocked() && invocation.getMethod().getName().indexOf("set") == 0) {
            throw new RuntimeException();
        }

        return super.invoke(invocation);
    }
}
