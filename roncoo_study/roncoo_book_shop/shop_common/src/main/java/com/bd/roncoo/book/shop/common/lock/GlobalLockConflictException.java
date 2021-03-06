package com.bd.roncoo.book.shop.common.lock;

/**
 * 分布式锁冲突异常
 */
public class GlobalLockConflictException extends RuntimeException {
    private static final long serialVersionUID = -4247606794782331656L;

    public GlobalLockConflictException(String lockPath) {
        super("get global lock failed, lockPath is " + lockPath);
    }
}
