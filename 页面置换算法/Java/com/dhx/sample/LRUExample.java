package com.dhx.sample;

import com.dhx.model.Element;
import com.dhx.algorithms.lru.LRU;

import static com.dhx.model.Constant.elements;

/**
 * @author dhx_
 * @className LruSample
 * @date : 2023/02/17/ 12:41
 **/
public class LRUExample {

    public static void main(String[] args) {
        System.out.println("测试前准备测试页如下: ");
        for (Element element : elements) {
            System.out.println(element);
        }
        // 理论上LRU算法的缺页率会随着 物理块数量的增加而降低 , 如果不明显, 可以通过加大第二个参数(访问次数)尝试
        testNBlock(3,100);
        testNBlock(4,100);
        testNBlock(6,100);
        testNBlock(8,100);
        testNBlock(10,100);
        /*
        实际测试 参数
        3 100 [测试结束] 缺页次数: 78缺页率: 0.78 当前页面情况: Pages:{ Element {key=2, val=10002}--> Element {key=4, val=10004}--> Element {key=9, val=10009} }
        4 100 [测试结束] 缺页次数: 65缺页率: 0.65 当前页面情况: Pages:{ Element {key=1, val=10001}--> Element {key=2, val=10002}--> Element {key=4, val=10004}--> Element {key=7, val=10007} }
        6 100 [测试结束] 缺页次数: 53缺页率: 0.53 当前页面情况: Pages:{ Element {key=1, val=10001}--> Element {key=3, val=10003}--> Element {key=4, val=10004}--> Element {key=6, val=10006}--> Element {key=8, val=10008}--> Element {key=10, val=10010} }
        8 100 [测试结束] 缺页次数: 34缺页率: 0.34 当前页面情况: Pages:{ Element {key=1, val=10001}--> Element {key=2, val=10002}--> Element {key=3, val=10003}--> Element {key=4, val=10004}--> Element {key=6, val=10006}--> Element {key=7, val=10007}--> Element {key=8, val=10008}--> Element {key=10, val=10010} }
        10 100 [测试结束] 缺页次数: 0缺页率: 0.0 当前页面情况: Pages:{ Element {key=1, val=10001}--> Element {key=2, val=10002}--> Element {key=3, val=10003}--> Element {key=4, val=10004}--> Element {key=5, val=10005}--> Element {key=6, val=10006}--> Element {key=7, val=10007}--> Element {key=8, val=10008}--> Element {key=9, val=10009}--> Element {key=10, val=10010} }
         */
    }

    /**
     * 随机调用十次页数
     * @param n 设置物理块数量为n
     * @param times 测试次数
     */
    static void testNBlock(int n,int times){
        System.out.println("\u001B[31m\u001B[1m[测试开始]\u001B[0m 本次测试物理块数量为 "+n);
        if(n>10){
            throw new RuntimeException("测试块数过多!");
        }
        // 设置lru序列的容量为n
        LRU cache = new LRU(n);
        // 假设我们最开始直接放入3个页面到内存块中，
        for (int i = 0; i < n; i++) {
            cache.put(elements[i]);
        }
        for (int i = 1; i <= times; i++) {
            cache.get((int)(Math.random()*100)%10+1);
        }
        System.out.println("\u001B[31m\u001B[1m[测试结束]\u001B[0m 缺页次数: "+cache.getMissingNum() + "缺页率: "+cache.getMissingNum()/(double)times + " 当前页面情况: " + cache.toString() );
    }
}
