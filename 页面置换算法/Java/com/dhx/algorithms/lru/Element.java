package com.dhx.algorithms.lru;

/**
 * @author dhx_
 * @className Node
 * @date : 2023/02/17/ 19:28
 **/
public class Element {

    /**
     * K V
     */
    public int key, val;

    /**
     * 下一个节点
     */
    public Element next;

    /**
     *  前一个节点
     */
    public Element pre;

    public Element(int k, int v) {
        this.key = k;
        this.val = v;
    }

    @Override
    public String toString() {
        return "\u001B[34m\u001B[1mElement \u001B[0m{" +
                "key=" + key +
                ", val=" + val +
                '}';
    }
}
