package com.bd.java.cache.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 缓存可以通过哈希表实现，为这个缓存增加大小限制实现缓存回收:
 * 1 查询出最近最少使用的元素
 * 2 给最近使用的元素做一个标记
 * 链表可以实现这两个操作
 * 检测最近最少使用的元素只需要返回链表尾节点
 * 标记一个元素为最近使用的元素只需要将其从当前位置移除，然后将该元素添加到链表头部
 * 此处重点是如何快速在链表中定位该元素
 * 哈希表可以在常量时间内索引到某个元素
 * 创建一个形如(key->链表节点)的哈希表，就能够在常量时间内定位到最近使用的元素
 * 即能够在常量时间内判断元素的是否存在或不存在
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private int cacheSize;

    public LRUCache(int cacheSize) {
        /*
            注意修改链表的顺序是插入的顺序，而不是访问的顺序
            LinkHashMap构造函数的参数accessOrder指定顺序模式
            见https://docs.oracle.com/javase/7/docs/api/java/util/LinkedHashMap.html#LinkedHashMap(int,%20float,%20boolean)
         */
        super(16, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    /**
     * 重写方法覆盖回收策略
     * 见http://docs.oracle.com/javase/7/docs/api/java/util/LinkedHashMap.html#removeEldestEntry(java.util.Map.Entry)
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() >= cacheSize;
    }
}
