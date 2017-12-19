package com.bd.roncoo.book.shop.common.lock;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GlobalLock {
    /**
     * 锁的路径
     */
    String path() default "";

    /**
     * 锁的key，一个Spring表达式。锁的路径和锁的key一起作为锁的标识，使用标识在Redis中创建锁
     * <p>
     * 多个实例竞争同一个标识相同的分布式锁
     */
    String key() default "";

    /**
     * 获取锁的等待时间，单位是毫秒，默认为0，如果获取不到锁就立即抛出异常
     * 注意，这里是获取锁的等待时间，如果两个并发线程试图获取同一个锁，
     * 等待时间是50毫秒，如果获取到锁的线程在50毫秒内处理完毕并释放锁，另一个线程是可以拿到锁的
     * 所以服务的幂等性还需要服务自身来保证
     * 由于添加@DistributedLock的方法在没拿到锁时至少会等待waitTime参数指定的时间，所以waitTime应该尽可能小，以避免长时间的等待
     * 由于拿不到锁的线程会抛出DistributedLockConflictException，所以如果不希望因为全局锁导致方法失败，waitTime应该有一个定值
     * 以便在持有锁的线程释放锁时当前线程可以拿到锁并继续处理
     * 反之如果希望拿不到锁时立即抛出异常，只要将waitTime设为0即可
     */
    long waitTime() default 0;

    /**
     * 锁的租约时间，单位是秒，默认120秒。当前线程在获取到锁以后，租约时间到期会自动释放锁。如果租约时间到期之前，方法执行完毕，也会释放锁
     */
    long leaseTime() default 120;
}
