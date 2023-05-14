//
// Created by 侯金科 on 2023/5/6.
//

#include <map>

#include "../model/CacheNode.h"



class LRU{
    int capacity;//页容量
    CacheNode *head, *tail;//头尾指针
    std::map<int, CacheNode *> mp;
    int times;//次数
    int MissingNum;//缺页次数
public:
    explicit LRU(int capacity){
        this->capacity = capacity;
        head = nullptr;
        tail = nullptr;
        times = 0;
        MissingNum = 0;
    }

    int get(int key){
        auto it = mp.find(key);//map<int, CacheNode *>::iterator it = mp.find(key);
        times ++;
        if(it != mp.end()){
            CacheNode *node = it -> second;
            remove(node);
            setHead(node);
            return node -> value;
        }else{
            MissingNum++;
            set(key, 1);
            return -1;
        }
    }

    void set(int key, int value){
        auto *newNode = new CacheNode(key, value);
        if(mp.size() >= capacity){
            auto iter = mp.find(tail -> key);
            remove(tail);
            mp.erase(iter);
        }
        setHead(newNode);
        mp[key] = newNode;

    }

    void remove(CacheNode *node){
        if(node -> pre != nullptr)
            node -> pre -> next = node -> next;
        else head = node -> next;
        if(node -> next != nullptr)
            node -> next -> pre = node -> pre;
        else
            tail = node -> pre;
    }

    void setHead(CacheNode *node){
        node -> next = head;
        node -> pre = nullptr;
        if(head != nullptr)
            head -> pre = node;
        head = node;
        if(tail == nullptr){
            tail = head;
        }
    }

    int getTimes() const {
        return times;
    }

    int getMissingNum() const {
        return MissingNum;
    }
};

/*
int main(){

    //解决控制条乱码问题
    system("chcp 65001");
    LRU *lruCache = new LRU(2);
    lruCache -> get(2);
    lruCache -> get(1);
    cout << lruCache -> get(2) << endl;
    lruCache -> get(4);
    cout << lruCache -> get(1) << endl;
    cout << lruCache -> get(2) << endl;
    cout << "缺页次数：" << lruCache->getMissingNum()<<endl;
    cout << "缺页率为：" << 1.0* lruCache->getMissingNum()/lruCache->getTimes()<<endl;
    cout << "hhh" << endl;
    return 0;
}*/
