package com.dhx.algorithms.lru;

/**
 * @author dhx_
 * @className Node
 * @date : 2023/02/17/ 19:28
 **/
public class Node {

    /**
     * K V
     */
    public int key, val;

    /**
     * 下一个节点
     */
    public Node next;

    /**
     *  前一个节点
     */
    public Node pre;

    public Node(int k, int v) {
        this.key = k;
        this.val = v;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                ", val=" + val +
                '}';
    }
}
