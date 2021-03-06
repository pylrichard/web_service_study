package com.bd.roncoo.eshop.common.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 加锁就是创建某个product id对应的一个临时ZNode
 *
 * ZK保证只会创建一个临时ZNode，其它请求如果再要创建临时ZNode，就会触发NodeExistsException
 * 如果临时ZNode创建成功，说明成功加锁，此时可以执行对Redis数据的操作
 * 如果临时ZNode创建失败，说明其它服务已经获取到锁，在操作Redis中的数据，那么就不断等待，直到可以获取到锁为止
 *
 * 释放锁就是删除临时ZNode，此时其他服务可以成功创建临时ZNode，获取到锁
 */
public class ZooKeeperSession {
    /**
     * 使用CountDownLatch进行通知会话创建完成结果
     */
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ZooKeeper zookeeper;

    public ZooKeeperSession() {
        //因为连接ZK Server创建会话时是异步执行，所以使用监听器通知何时完成与ZK Server的连接
        try {
            this.zookeeper = new ZooKeeper(
                    "192.168.8.10:2181,192.168.8.11:2181,192.168.8.12:2181",
                    50000,
                    new ZooKeeperWatcher());
            try {
                /*
                    其它线程/其它函数可以调用countDown()减1
					调用CountDownLatch.await()判断是否等于0，不等于则进行等待
					相等则之前调用await()的线程会恢复执行
				*/
                connectedSemaphore.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("ZooKeeper session established......");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取分布式锁，没有获取到就循环等待，直到获取到锁
     */
    public void acquireDistributedLock(Long productId) {
        String path = "/product-lock-" + productId;
        try {
            zookeeper.create(path, "".getBytes(),
                    Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            logger.info("success to acquire lock for product[id=" + productId + "]");
        } catch (Exception e1) {
            //如果商品对应的锁的ZNode已经存在，会触发NodeExistsException
            int count = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                    zookeeper.create(path, "".getBytes(),
                            Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                } catch (Exception e2) {
                    count++;
                    logger.info("the " + count + " times try to acquire lock for product[id="
                            + productId + "]......");
                    continue;
                }
                logger.info("success to acquire lock for product[id=" + productId + "] after "
                        + count + " times try......");
                break;
            }
        }
    }

    /**
     * 获取分布式锁，没有获取到就循环等待，直到获取到锁
     */
    public void acquireDistributedLock(String path) {
        try {
            zookeeper.create(path, "".getBytes(),
                    Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            logger.info("success to acquire lock for " + path);
        } catch (Exception e1) {
            int count = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                    zookeeper.create(path, "".getBytes(),
                            Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                } catch (Exception e2) {
                    count++;
                    logger.info("the " + count + " times try to acquire lock for " + path + "......");
                    continue;
                }
                logger.info("success to acquire lock for " + path + " after " + count + " times try......");
                break;
            }
        }
    }

    /**
     * 获取分布式锁，没有获取到就返回false
     */
    public boolean acquireFastFailedDistributedLock(String path) {
        try {
            zookeeper.create(path, "".getBytes(),
                    Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            logger.info("success to acquire lock for " + path);
            return true;
        } catch (Exception e) {
            logger.info("fail to acquire lock for " + path);
        }

        return false;
    }

    /**
     * 释放分布式锁
     */
    public void releaseDistributedLock(Long productId) {
        String path = "/product-lock-" + productId;
        try {
            zookeeper.delete(path, -1);
            logger.info("release the lock for product[id=" + productId + "]......");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放分布式锁
     */
    public void releaseDistributedLock(String path) {
        try {
            zookeeper.delete(path, -1);
            logger.info("release the lock for " + path + "......");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNodeData(String path) {
        try {
            return new String(zookeeper.getData(path, false, new Stat()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public void setNodeData(String path, String data) {
        try {
            zookeeper.setData(path, data.getBytes(), -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createNode(String path) {
        try {
            zookeeper.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (Exception e) {
        }
    }

    /**
     * 建立ZK Session的Watcher
     */
    private class ZooKeeperWatcher implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            logger.info("Receive watched event: " + event.getState());
            /*
                完成与ZK Server的连接并创建会话
             */
            if (KeeperState.SyncConnected == event.getState()) {
                //唤醒等待的线程
                connectedSemaphore.countDown();
            }
        }
    }

    /**
     * 封装单例的静态内部类
     */
    private static class Singleton {
        private static ZooKeeperSession instance;

        static {
            instance = new ZooKeeperSession();
        }

        public static ZooKeeperSession getInstance() {
            return instance;
        }
    }

    /**
     * 获取单例
     */
    public static ZooKeeperSession getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 初始化单例
     */
    public static void init() {
        getInstance();
    }
}
