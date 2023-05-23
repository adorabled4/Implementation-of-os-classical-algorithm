package com.dhx.algorithms.lru;

import com.dhx.model.DoubleLinkedList;
import com.dhx.model.Element;

import java.util.HashMap;

import static com.dhx.model.Constant.elements;

/**
 * @author dhx_
 * @className LRUCache
 * @date : 2023-5-5 17:40:16
 **/
public class LRU {

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
    public static final int DEFAULT_CAPACITY=10;
    

    /**
     *
     * @param capacity LRU容量
     */
    public LRU(int capacity) {
        if(capacity<0){
            this.cap=DEFAULT_CAPACITY;
        }else{
            this.cap = capacity;
        }
        map = new HashMap<>();
        missingNum=0;
        cache = new DoubleLinkedList();
    }

    /**
     * 将某个 key 提升为最近使用的
     * @param key
     */
    private void makeRecently(int key) {
        Element x = map.get(key);
        // 先从链表中删除这个节点
        cache.remove(x);
        // 重新插到队尾
        cache.addLast(x);
    }

    /**
     * 添加最近使用的元素
     * @param key
     * @param val
     */
    private void addRecently(int key, int val) {
        Element x = new Element(key, val);
        // 链表尾部就是最近使用的元素
        cache.addLast(x);
        // 别忘了在 map 中添加 key 的映射
        map.put(key, x);
    }

    /**
     * 删除某一个 key
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
     *  删除最久未使用的元素
     */
    private void removeLeastRecently() {
        // 链表头部的第一个元素就是最久未使用的
        Element deletedElement = cache.removeFirst();
        // 同时别忘了从 map 中删除它的 key
        int deletedKey = deletedElement.key;
        map.remove(deletedKey);
    }

    /**
     * 获取key对应的value
     * @param key
     * @return
     */
    public int get(int key) {
        if (!map.containsKey(key)) {
            System.out.println("\u001B[35m\u001B[1m[MISS]\u001B[0m发生缺页中断,调入页: " + elements[key]);
            if(key>10){
                throw new RuntimeException("key范围超出测试数据范围!");
            }
            put(elements[key]);
            missingNum++;
            return map.get(elements[key].key).val;
        }
        // 将该数据提升为最近使用的
        System.out.println("\u001B[35m\u001B[1m[USE]\u001B[0m从cache使用页，Data: " + map.get(key));
        makeRecently(key);
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
     * 向LRU中添加数据
     * @param key
     * @param val
     */
    public void put(int key,int val){
        Element element = new Element(key, val);
        System.out.println("\u001B[32m\u001B[1m[ADD]\u001B[0m添加页到cache，Data: " + element);
        if (map.containsKey(key)) {
            // 删除旧的数据
            deleteKey(key);
            // 新插入的数据为最近使用的数据
            addRecently(key, val);
            return;
        }
        if (cap == cache.size()) {
            // 删除最久未使用的元素
            removeLeastRecently();
        }
        // 添加为最近使用的元素
        addRecently(key, val);
    }

    /**
     * 向LRU中添加数据
     */
    public void put(Element element){
        int key = element.key;
        int val=element.val;
        System.out.println("\u001B[32m\u001B[1m[ADD]\u001B[0m添加页到cache，Data: " + element);
        if (map.containsKey(key)) {
            // 删除旧的数据
            deleteKey(key);
            // 新插入的数据为最近使用的数据
            addRecently(key, val);
            return;
        }
        if (cap == cache.size()) {
            // 删除最久未使用的元素
            removeLeastRecently();
        }
        // 添加为最近使用的元素
        addRecently(key, val);
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Pages:{ ");
        map.entrySet().forEach(item->{
            Element value = item.getValue();
            stringBuffer.append(value+"--> ");
        });
        return stringBuffer.substring(0,stringBuffer.length()-4)+" }".toString();

    }
}
