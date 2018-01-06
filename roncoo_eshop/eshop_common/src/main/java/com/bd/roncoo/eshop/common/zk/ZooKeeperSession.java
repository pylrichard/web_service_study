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

public class ZooKeeperSession {
    /**
     * 使用CountDownLatch进行多线程并发同步，传入的数字参数代表
     */
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ZooKeeper zookeeper;

    public ZooKeeperSession() {
        //因为连接Zookeeper Server创建会话时是异步执行，所以使用监听器通知何时完成与ZK Server的连接
        try {
            this.zookeeper = new ZooKeeper(
                    "192.168.8.10:2181,192.168.8.11:2181,192.168.8.12:2181",
                    50000,
                    new ZooKeeperWatcher());
            try {
                /*
                    其他的线程可以调用countDown()减1
					调用CountDownLatch.await()判断是否等于0，不等于则进行等待
					相等的话则之前调用await()的线程会恢复执行
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
     * 获取分布式锁
     */
    public void acquireDistributedLock(Long productId) {
        String path = "/product-lock-" + productId;
        try {
            zookeeper.create(path, "".getBytes(),
                    Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            logger.info("success to acquire lock for product[id=" + productId + "]");
        } catch (Exception e) {
            //如果商品对应的锁的node已经存在，会触发NodeExistsException
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
     * 获取分布式锁
     */
    public void acquireDistributedLock(String path) {
        try {
            zookeeper.create(path, "".getBytes(),
                    Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            logger.info("success to acquire lock for " + path);
        } catch (Exception e) {
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
     * 获取分布式锁
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
            if (KeeperState.SyncConnected == event.getState()) {
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
