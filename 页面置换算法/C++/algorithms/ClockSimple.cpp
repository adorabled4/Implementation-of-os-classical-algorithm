//
// Created by 侯金科 on 2023/5/8.
//
#include "../model/Mem.h"
#include <iostream>
const int N = 20;

class ClockSimple {
    int capacity;//页容量
    int idx;//内存指针
    int times;//调度总次数
    int MissingNum;//缺页次数
    Mem mem[N];
public:
    ClockSimple(int capacity) {
        this->capacity = capacity;
        idx = 0;
        times = 0;
        MissingNum = 0;
    }

    int get(int x) {
        int tmp = -1;
        times++;
        for (int i = 0; i < capacity; i++) {
            if (mem[i].pageNum == x) tmp = i;
        }
        if (tmp) {
            idx = tmp;
            mem[idx].visit = 1;
        } else {
            //如果内存没有
            MissingNum++;    //缺页次数++
            while (true) {
                //遇到访问位为1,置0
                if (mem[idx].visit == 1) {
                    mem[idx].visit = 0;
                } else {    //访问位为0，置换页面
                    mem[idx].pageNum = x;
                    mem[idx].visit = 1;
                    break;
                }
                //指针循环遍历
                idx++;
                if (idx == capacity) idx = 0;
            }
        }
        return tmp;//返回现存内存块，-1表示不在
    }

    int getTimes() const {
        return times;
    }

    int getMissingNum() const {
        return MissingNum;
    }
};

//int main(){
//    system("chcp 65001");
//
//    auto clockSimple = ClockSimple(4);
//
//    std::cout<<clockSimple.get(2)<<std::endl;
//    std::cout<<clockSimple.get(2)<<std::endl;
//    std::cout<<clockSimple.get(2)<<std::endl;
//    std::cout<<clockSimple.get(2)<<std::endl;
//    std::cout<<clockSimple.get(2)<<std::endl;
//
////    std::cout << "\u001B[31m\u001B[1m[测试结束]\u001B[0m 缺页次数: "
////              << clockSimple.getMissingNum() << "  总次数：" << clockSimple.getTimes()
////              << "  缺页率: " << 1.0 * clockSimple.getMissingNum() / clockSimple.getTimes() << std::endl;
//}