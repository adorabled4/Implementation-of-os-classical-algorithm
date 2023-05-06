//
// Created by 侯金科 on 2023/5/6.
//
#include <iostream>
#include <map>
using namespace std;

class CacheNode{
public:
    int key;
    int value;
    CacheNode *pre, *next;//节点的前驱、后继指针

    CacheNode(int key, int value) : key(key), value(value) {}
};

class LRUCache{
    int capacity;//页容量
    CacheNode *head, *tail;//头尾指针
    map<int, CacheNode *> mp;
    int times;//次数
    int MissingNum;//缺页次数
public:
    LRUCache(int capacity){
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
            return -1;
        }
    }

    void set(int key, int value){
        auto it = mp.find(key);
        if(it != mp.end()){
            CacheNode * node = it -> second;
            node -> value = value;
            remove(node);
            setHead(node);
        }
        else{
            auto *newNode = new CacheNode(key, value);
            if(mp.size() >= capacity){
                auto iter = mp.find(tail -> key);
                remove(tail);
                mp.erase(iter);
            }
            setHead(newNode);
            mp[key] = newNode;
        }
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

};



int main(){
    LRUCache *lruCache = new LRUCache(2);
    lruCache -> set(2, 1);
    lruCache -> set(1, 1);
    cout << lruCache -> get(2) << endl;
    lruCache -> set(4, 1);
    cout << lruCache -> get(1) << endl;
    cout << lruCache -> get(2) << endl;
    cout << "hhh" << endl;
    return 0;
}