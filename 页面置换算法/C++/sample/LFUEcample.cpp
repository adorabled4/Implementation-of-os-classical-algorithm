//
// Created by 侯金科 on 2023/5/7.
//

#include "../algorithms/LFU.cpp"
#include <ctime>
#include <random>
#include <iostream>

time_t seed = time(nullptr);
//创建一个引擎。命名为eng，并用指定种子初始化随机数序列。
std::default_random_engine eng(seed);
std::uniform_int_distribution<int> dist(1, 10);//创建一个分布。
std::uniform_int_distribution<int> dist2(1, 100);//创建一个分布。

void testNBlock(int n,int times){
    std::cout<<"\u001B[31m\u001B[1m[测试开始]\u001B[0m 本次测试物理块数量为 "<<n << std::endl;

    // 设置lru序列的容量为n
    LFU *cache = new LFU(n);
    // 假设我们最开始直接放入3个页面到内存块中，

    for (int i = 1; i <= times; i++) {
        cache->get((int)(dist(eng)));
    }

    std::cout << "\u001B[31m\u001B[1m[测试结束]\u001B[0m 缺页次数: "
              << cache->getMissingNum()<<"  总次数：" << cache->getTimes()
              <<"  缺页率: "<<1.0*cache->getMissingNum()/times << std::endl;

}

int main(){
    //解决控制条乱码问题
    system("chcp 65001");
    //可以设置测试次数
    int num = 5;
    std::cout << "测试前准备测试页如下: " << std::endl;

    for(int i=0; i< num;i++)
        testNBlock(dist(eng),dist2(eng));

    return 0;
}