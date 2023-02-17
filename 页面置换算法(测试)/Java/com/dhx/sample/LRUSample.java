package com.dhx.sample;

import com.dhx.algorithms.LRU;

/**
 * @author dhx_
 * @className LruSample
 * @date : 2023/02/17/ 12:41
 **/
public class LRUSample {

    public static void main(String[] args) {
        LRU cache = new LRU(3);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.get(1);
        cache.put(4, 3);
        System.out.println(cache);
    }
}
