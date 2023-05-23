//
// Created by 侯金科 on 2023/5/6.
//

#ifndef IMPLEMENTATION_OF_OS_CLASSICAL_ALGORITHM_CACHENODE_H
#define IMPLEMENTATION_OF_OS_CLASSICAL_ALGORITHM_CACHENODE_H
class CacheNode{
public:
    int key;
    int value;
    CacheNode *pre, *next;//节点的前驱、后继指针

    CacheNode(int key, int value) : key(key), value(value) {}

};


#endif //IMPLEMENTATION_OF_OS_CLASSICAL_ALGORITHM_CACHENODE_H
