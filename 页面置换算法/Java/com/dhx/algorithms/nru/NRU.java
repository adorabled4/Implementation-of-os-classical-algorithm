package com.dhx.algorithms.nru;

import com.dhx.model.Element;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static com.dhx.model.Constant.elements;

/**
 * @author adorabled4
 * @className NRU
 * @date : 2023/05/09/ 12:45
 **/
public class NRU {

    /**
     * key -> callStatus 访问状态存储
     */
    private Map<Integer, Integer> callMap;
    /**
     * key -> Element(key, val)
     */
    private Map<Integer, Element> map;

    /**
     * Element(k1, v1) <-> Element(k2, v2)...
     */
    private LinkedList<Element> cache;

    /**
     * 每次用于遍历的指针
     */
    private Element startPtr;

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
     * @param capacity NRU容量
     */
    public NRU(int capacity) {
        if (capacity < 0) {
            this.cap = DEFAULT_CAPACITY;
        } else {
            this.cap = capacity;
        }
        map = new HashMap<>();
        callMap = new HashMap<>();
        missingNum = 0;
        cache = new LinkedList<>();
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
     * 获取key对应的value
     *
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
            return elements[key].val;
        } else {
            System.out.println("\u001B[35m\u001B[1m[USE]\u001B[0m从cache使用页，Data: " + map.get(key));
            update(key);
            makeRecently(key);
            return map.get(key).val;
        }
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
     * 将某个key提升为最近使用的
     *
     * @param key
     */
    private void makeRecently(int key) {
        Element x = map.get(key);
        // 先从链表中删除这个节点
        cache.remove(x);
        // 重新插入到队尾
        cache.addLast(x);
    }


    /**
     * 删除最久未使用的元素
     */
    private void removeLeastRecently() {
        // 链表头部的元素就是最久未使用的
        Element deletedNode = cache.removeFirst();
        // 同时在 map 中删除它的 key，以便释放空间
        int deletedKey = deletedNode.key;
        map.remove(deletedKey);
        System.out.println("\u001B[33m\u001B[1m[REMOVE]\u001B[0m删除页，Data: " + deletedNode);
    }

    /**
     * 更新某个节点的使用状态
     *
     * @param key
     */
    private void update(int key) {
        Integer callStatus = callMap.getOrDefault(key, 0);
        callMap.put(key, callStatus + 1);
    }


    /**
     * 添加元素
     * @param element
     */
    private void addElement(Element element) {
        cache.add(element);
        map.put(element.key,element);
        callMap.put(element.key, 1);
    }

    /**
     * 向NRU中添加数据
     */
    public void put(Element element) {
        if (cache.size() >= cap) {
            // 需要利用指针, 进行替换
            if(startPtr==null){
                startPtr=cache.get(0);
            }
            // 指针循环, 扫描
            while(startPtr!=element){
                if(callMap.getOrDefault(startPtr.key,0)==0){// 如果当前的页面没有被访问过=>进行置换
                    startPtr=element;
                    // 进行置换
                    for (int i = 0; i < cache.size(); i++) {
                        if(cache.get(i).key==startPtr.key){
                            cache.set(i,element);
                            // 添加访问状态
                            callMap.put(element.key,1);
                            // 删除原本的位置的元素
                            map.remove(startPtr.key);
                            break;
                        }
                    }
                }else{// 使用过 , 指针后移
                    callMap.put(startPtr.key,0);
                    if(startPtr.next==null){
                        startPtr.next=cache.get(0);
                    }else{
                        startPtr=startPtr.next;
                    }
                }
            }
            return;
        }
        System.out.println("\u001B[32m\u001B[1m[ADD]\u001B[0m添加页到cache，Data: " + element);
        // 空间充足 , 正常添加
        if (map.containsKey(element.key)) {
            return;
        }else{
            addElement(element);
        }
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
