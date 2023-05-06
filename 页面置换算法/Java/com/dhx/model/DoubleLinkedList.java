package com.dhx.model;

/**
 * @author dhx_
 * @className DoubleList
 * @date : 2023-5-5 17:40:16
 **/
public class DoubleLinkedList {
    /**
     * 头尾虚节点
     */
    private Element head, tail;

    /**
     * 链表元素数
     */
    private int size;

    public DoubleLinkedList() {
        // 初始化双向链表的数据
        head = new Element(0, 0);
        tail = new Element(0, 0);
        head.next = tail;
        tail.pre = head;
        size = 0;
    }

    /**
     * 在链表尾部添加节点 x，时间 O(1)
     * @param x 需要添加的element
     */
    public void addLast(Element x) {
        x.pre = tail.pre;
        x.next = tail;
        tail.pre.next = x;
        tail.pre = x;
        size++;
    }

    /**
     * 删除链表中的 x 节点（x 一定存在） , 由于是双链表且给的是目标 element 节点，时间 O(1)
     * @param x
     */
    public void remove(Element x) {
        x.pre.next = x.next;
        x.next.pre = x.pre;
        size--;
    }

    /**
     * 删除链表中第一个节点，并返回该节点，时间 O(1)
     * @return
     */
    public Element removeFirst() {
        if (head.next == tail)
            return null;
        Element first = head.next;
        remove(first);
        return first;
    }

    /**
     * 返回链表长度，时间 O(1)
     * @return
     */
    public int size() { return size; }

}
