package com.dhx.sample;

import com.dhx.algorithms.fifo.FIFO;
import com.dhx.algorithms.lru.LRU;
import com.dhx.model.Element;

import java.util.ArrayList;
import java.util.List;

import static com.dhx.model.Constant.elements;

/**
 * @author dhx_
 * @className LFUSample
 * @date : 2023-5-5 15:13:42
 **/
public class FIFOExample {

    public static void main(String[] args) {
        System.out.println("测试前准备测试页如下: ");
        for (Element element : elements) {
            System.out.println(element);
        }
        // 理论上LRU算法的缺页率会随着 物理块数量的增加而降低 , 如果不明显, 可以通过加大第二个参数(访问次数)尝试
        List<String> results = new ArrayList<>();
        results.add(testNBlock(3, 1000));
        results.add(testNBlock(4, 1000));
        results.add(testNBlock(6, 1000));
        results.add(testNBlock(8, 1000));
        results.add(testNBlock(10, 1000));
        results.forEach(System.out::println);
        /*
            FIFO 算法由于不会对常使用的页面进行特殊处理，添加页面之后可能破坏原本的页面顺序，因此有可能发生Belady异常（这里的页面的数量较小，因此并不明显）
        实际情况是由于计算机的局部性原理，类似于LRU的算法的效果会更好
        测试时建议测试加大测试数据的容量，使得结果更加趋于一般效果
        [测试结束]物理块数: 3 缺页次数: 701 缺页率: 0.701
        [测试结束]物理块数: 4 缺页次数: 605 缺页率: 0.605
        [测试结束]物理块数: 6 缺页次数: 431 缺页率: 0.431
        [测试结束]物理块数: 8 缺页次数: 216 缺页率: 0.216
        [测试结束]物理块数: 10 缺页次数: 0 缺页率: 0.0
         */
    }

    /**
     * 随机调用十次页数
     * @param n 设置物理块数量为n
     * @param times 测试次数
     */
    static String testNBlock(int n,int times){
        System.out.println("\u001B[31m\u001B[1m[测试开始]\u001B[0m 本次测试物理块数量为 "+n);
        if(n>10){
            throw new RuntimeException("测试块数过多!");
        }
        // 设置lru序列的容量为n
        FIFO cache = new FIFO(n);
        // 假设我们最开始直接放入3个页面到内存块中，
        for (int i = 1; i <= n; i++) {
            cache.put(elements[i]);
        }
        for (int i = 1; i <= times; i++) {
            int random = (int)(Math.random()*100)%10+1;
            cache.get(random);
        }
        System.out.println("\u001B[31m\u001B[1m[测试结束]\u001B[0m 缺页次数: "+cache.getMissingNum() + "缺页率: "+cache.getMissingNum()/(double)times + " 当前页面情况: " + cache.toString() );
        return "\u001B[31m\u001B[1m[测试结束]\u001B[0m物理块数: "+n+" 缺页次数: "+cache.getMissingNum() + " 缺页率: "+cache.getMissingNum()/(double)times;
    }
}
