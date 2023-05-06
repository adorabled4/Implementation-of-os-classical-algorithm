package com.dhx.algorithms.fifo;

import com.dhx.model.DoubleLinkedList;
import com.dhx.model.Element;

import java.util.HashMap;
import java.util.LinkedList;

import static com.dhx.model.Constant.elements;

/**
 * @author adorabled4
 * @className FIFO 选择最早进入物理内存的页面进行淘汰。
 * @date : 2023/05/06/ 09:57
 **/
public class FIFO {

    /**
     * key -> Element(key, val)
     */
    private HashMap<Integer, Element> map;

    /**
     * 存储 Element(k1, v1) -> Element(k2, v2)...
     */
    private LinkedList<Element> cache;

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
     * @param capacity FIFO容量
     */
    public FIFO(int capacity) {
        if (capacity < 0) {
            this.cap = DEFAULT_CAPACITY;
        } else {
            this.cap = capacity;
        }
        map = new HashMap<>();
        missingNum = 0;
        cache = new LinkedList<>();
    }


    /**
     * 获取key对应的value
     *
     * @param key
     * @return
     */
    public Element get(int key) {
        if (!map.containsKey(key)) {
            System.out.println("\u001B[35m\u001B[1m[MISS]\u001B[0m发生缺页中断,调入页: " + elements[key]);
            if (key > 10) {
                throw new RuntimeException("key范围超出测试数据范围!");
            }
            put(elements[key]);
            missingNum++;
            return elements[key];
        }
        System.out.println("\u001B[35m\u001B[1m[USE]\u001B[0m从cache使用页，Data: " + map.get(key));
        return map.get(key);
    }

    /**
     * 获取缺页次数
     *
     * @return
     */
    public int getMissingNum() {
        return missingNum;
    }

    /**
     * 向FIFO中添加数据
     *
     * @param key
     * @param val
     */
    public void put(int key, int val) {
        Element element = new Element(key, val);
        System.out.println("\u001B[32m\u001B[1m[ADD]\u001B[0m添加页到cache，Data: " + element);
        // 1. 页面已经在内存中
        // 2. 没有在内存中
        if (!map.containsKey(key)) {
            // 需要进行置换
            if (cap == cache.size()) {
                // 注意需要从map中也删除element
                Element poll = cache.poll();
                map.remove(poll.key);
            }
            cache.addLast(element);
            map.put(element.key, element);
        }
    }

    /**
     * 向队列中添加数据
     */
    public void put(Element element) {
        System.out.println("\u001B[32m\u001B[1m[ADD]\u001B[0m添加页到cache，Data: " + element);
        // 1. 页面已经在内存中
        // 2. 没有在内存中
        if (!map.containsKey(element.key)) {
            while (cache.size()>=cap) {
                Element poll = cache.poll();
                map.remove(poll.key);
            }
            cache.addLast(element);
            map.put(element.key, element);
        }
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Pages:{ ");
        cache.forEach(item -> {
            stringBuffer.append(item + "--> ");
        });
        return stringBuffer.substring(0, stringBuffer.length() - 4) + " }".toString();
    }
}
