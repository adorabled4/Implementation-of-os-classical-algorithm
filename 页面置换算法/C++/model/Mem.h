//
// Created by 侯金科 on 2023/5/8.
//
#ifndef IMPLEMENTATION_OF_OS_CLASSICAL_ALGORITHM_CACHENODE_H
#define IMPLEMENTATION_OF_OS_CLASSICAL_ALGORITHM_CACHENODE_H
#include <iostream>
#include <queue>
#include<iomanip>
using namespace std;

class Mem {	//内存
public:
    int pageNum;	//页面号
    int visit;	//访问位
    int modify;//修改位
};

#endif //IMPLEMENTATION_OF_OS_CLASSICAL_ALGORITHM_CACHENODE_H
