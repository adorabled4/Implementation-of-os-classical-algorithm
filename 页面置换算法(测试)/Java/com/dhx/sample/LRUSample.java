package com.dhx.sample;

import com.dhx.algorithms.lru.LRUCache;

/**
 * @author dhx_
 * @className LruSample
 * @date : 2023/02/17/ 12:41
 **/
public class LRUSample {

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(3); // 设置lru序列的容量为3
        // 添加三个前置数据 ,  保证此时lru序列已满
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        System.out.println("the first Node :"+cache.get(1));
        cache.put(4, 3);
        System.out.println(cache);
    }
}
