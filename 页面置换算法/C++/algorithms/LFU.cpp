//
// Created by 侯金科 on 2023/5/6.
//

#include <map>

#include "../model/CacheNode.h"

class LFU{
    int capacity;//页容量
    CacheNode *head, *tail;//头尾指针
    std::map<int, CacheNode *> mp;
    int times;//次数
    int MissingNum;//缺页次数
public:
    explicit LFU(int capacity){
        this->capacity = capacity;
        head = nullptr;
        tail = nullptr;
        times = 0;
        MissingNum = 0;
    }

    //调出key页面，若没有则创建
    int get(int key){
        auto it = mp.find(key);//map<int, CacheNode *>::iterator it = mp.find(key);
        times ++;
        if(it != mp.end()){
            CacheNode *node = it -> second;
            node->value++;//调整节点的访问次数
            return node -> value;
        }else{
            MissingNum++;
            set(key, 1);
            return -1;
        }
    }

    //调入key页面
    void set(int key, int value){
        auto *newNode = new CacheNode(key, value);
        if(mp.size() >= capacity){
            setHead(newNode);
            remove1(newNode);
        }else
            setHead(newNode);
        mp[key] = newNode;

    }

    //从尾部遍历,避免先调入的调出
    void remove1(CacheNode *node){
        CacheNode * min = tail, *p = tail;

        while(p->pre != node){
            p = p -> pre;
            if(min->value > p->value)
                min = p;
        }

        remove(min);
        mp.erase(mp.find(min->key));
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

//可以在这里直接调试，输出-1表示缺页,用cout和endl记得加std
/*
int main(){

    return 0;
}
*/
