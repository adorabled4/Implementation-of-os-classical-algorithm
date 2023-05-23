//
// Created by 侯金科 on 2023/5/8.
//
#include "../model/Mem.h"

const int N = 20;

class ClockUpdate {
    int capacity;//页容量
    int idx;//内存指针
    int times;//调度总次数
    int MissingNum;//缺页次数
    Mem mem[N];
public:
    ClockUpdate(int capacity) {
        this->capacity = capacity;
        idx = 0;
        times = 0;
        MissingNum = 0;
    }

    void get(int x) {
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
            for (int i = idx; i < idx+capacity; i++){
                //遇到访问位为1,置0
                if (mem[idx].visit == 1) {
                    mem[idx].visit = 0;
                } else if(mem[idx].modify==0){
                    mem[idx].pageNum = x;
                    mem[idx].visit = 1;
                    mem[idx].modify = 1;
                    break;
                }
                //指针循环遍历
                idx++;
                if (idx == capacity) idx = 0;
            }
            if(mem[idx].pageNum!=x){
                while(true){
                    //遇到访问位为1,置0
                    if (mem[idx].modify == 1){
                        mem[idx].pageNum = x;
                        mem[idx].visit = 1;
                        mem[idx].modify = 1;
                        break;
                    }
                    //指针循环遍历
                    idx++;
                    if (idx == capacity) idx = 0;
                }
            }
        }
        //return tmp;//返回现存内存块，-1表示不在
    }

    int getTimes() const {
        return times;
    }

    int getMissingNum() const {
        return MissingNum;
    }
};
