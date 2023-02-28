package com.dhx.algorithms.lru;

/**
 * @author dhx_
 * @className Node
 * @date : 2023/02/17/ 19:28
 **/
public class Node {
    public int key, val;
    public Node next;
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
