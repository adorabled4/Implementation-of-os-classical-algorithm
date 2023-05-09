package com.dhx.algorithms.lfu;

import com.dhx.model.DoubleLinkedList;
import com.dhx.model.Element;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.dhx.model.Constant.elements;

/**
 * @author adorabled4
 * @className LFU 最不经常使用算法-LFU
 * @date : 2023/05/09/ 10:18
 **/
public class LFU {

    /**
     * 用于存储使用次数 , 记录使用次数  key : callTimes
     */
    Map<Integer, Integer> callTimes;
    /**
     * key -> Element(key, val)
     */
    private HashMap<Integer, Element> map;

    /**
     * Element(k1, v1) <-> Element(k2, v2)...
     */
    private DoubleLinkedList cache;

    /**
     * max capacity
     */
    private int cap;

    /**
     * 统计缺页次数
     */
    private int missingNum;

    /**
     * default capacity
     */
    public static final int DEFAULT_CAPACITY = 10;


    /**
     * @param capacity LFU容量
     */
    public LFU(int capacity) {
        if (capacity < 0) {
            this.cap = DEFAULT_CAPACITY;
        } else {
            this.cap = capacity;
        }
        map = new HashMap<>();
        callTimes = new HashMap<>();
        missingNum = 0;
        cache = new DoubleLinkedList();
    }


    /**
     * 删除某一个 key
     *
     * @param key
     */
    private void deleteKey(int key) {
        Element x = map.get(key);
        // 从链表中删除
        cache.remove(x);
        // 从 map 中删除
        map.remove(key);
    }

    /**
     * 删除使用次数最少的元素
     */
    private void removeLeastUse() {
        Integer leastUse = getLeastUse();
        Element element = map.get(leastUse);
        map.remove(leastUse);
        cache.remove(element);
        callTimes.remove(leastUse);
    }

    /**
     * @return 返回使用次数最少得页面的key
     */
    private Integer getLeastUse() {
        final Integer[] key = {null};
        final Integer[] minCount = {Integer.MAX_VALUE};
        callTimes.entrySet().forEach(item -> {
            if (item.getValue() < minCount[0]) {
                minCount[0] = item.getValue();
                key[0] = item.getKey();
            }
        });
        return key[0];
    }

    /**
     * 统计页面的调用次数+1
     * @param key
     * @return
     */
    private void increaseCallTime(int key) {
        Integer oldCallTime = callTimes.getOrDefault(key, 0);
        callTimes.put(key, oldCallTime + 1);
    }

    /**
     * 获取key对应的value
     * @param key
     * @return
     */
    public int get(int key) {
        if (!map.containsKey(key)) {
            System.out.println("\u001B[35m\u001B[1m[MISS]\u001B[0m发生缺页中断,调入页: " + elements[key]);
            if (key > 10) {
                throw new RuntimeException("key范围超出测试数据范围!");
            }
            put(elements[key]);
            missingNum++;
            return map.get(elements[key].key).val;
        }
        System.out.println("\u001B[35m\u001B[1m[USE]\u001B[0m从cache使用页，Data: " + map.get(key));
        increaseCallTime(key);
        return map.get(key).val;
    }

    /**
     * 获取缺页次数
     * @return
     */
    public int getMissingNum() {
        return missingNum;
    }

    /**
     * 向LFU中添加数据
     *
     * @param key
     * @param val
     */
    public void put(int key, int val) {
        Element element = new Element(key, val);
        System.out.println("\u001B[32m\u001B[1m[ADD]\u001B[0m添加页到cache，Data: " + element);
        if (map.containsKey(key)) {
            // 删除旧的数据
            deleteKey(key);
            // 新插入的数据为最近使用的数据
            return;
        }
        if (cap == cache.size()) {
            // 删除使用次数最少的元素
            removeLeastUse();
        }
        addElement(element);
    }

    /**
     * 向LFU中添加数据
     */
    public void put(Element element) {
        int key = element.key;
        System.out.println("\u001B[32m\u001B[1m[ADD]\u001B[0m添加页到cache，Data: " + element);
        if (map.containsKey(key)) {
            // 删除旧的数据
            deleteKey(key);
            // 新插入的数据为最近使用的数据
            return;
        }
        if (cap == cache.size()) {
            // 删除使用次数最少的元素
            removeLeastUse();
        }
        addElement(element);
    }

    /**
     * 添加元素
     */
    private void addElement(Element element) {
        cache.addLast(element);
        map.put(element.key, element);
        // 设置使用的次数为1
        callTimes.put(element.key, 0);
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Pages:{ ");
        map.entrySet().forEach(item -> {
            Element value = item.getValue();
            stringBuffer.append(value + "--> ");
        });
        return stringBuffer.substring(0, stringBuffer.length() - 4) + " }".toString();
    }
}
