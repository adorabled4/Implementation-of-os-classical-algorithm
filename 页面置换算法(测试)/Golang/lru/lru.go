package lru

import (
	"container/list"
	"fmt"
)

type LRUCache struct {
	cap   int
	cache map[int]int
	list  *list.List // 导入包
}

func Constructor(capacity int) LRUCache {
	return LRUCache{
		cap:   capacity,
		cache: make(map[int]int),
		list:  list.New(),
	}
}

func (this *LRUCache) Get(key int) int {
	if val, ok := this.cache[key]; ok {
		// 将 key 移动到链表尾部表示最近访问
		this.makeRecently(key)
		return val
	}
	return -1
}

func (this *LRUCache) Put(key int, value int) {
	if _, ok := this.cache[key]; ok {
		// 修改 key 的值，将 key 移动到链表尾部表示最近访问
		this.cache[key] = value
		this.makeRecently(key)
		return
	}

	if len(this.cache) >= this.cap {
		// 链表头部就是最久未使用的 key
		this.removeOldest()
	}
	// 将新的 key-value 添加链表尾部
	this.cache[key] = value
	this.list.PushBack(key)
}

func (this *LRUCache) makeRecently(key int) {
	// 将 key 移动到链表尾部表示最近访问
	for e := this.list.Front(); e != nil; e = e.Next() {
		if e.Value.(int) == key {
			this.list.MoveToBack(e)
			break
		}
	}
}

func (this *LRUCache) removeOldest() {
	// 删除链表头部表示最久未使用的 key
	e := this.list.Front()
	delete(this.cache, e.Value.(int))
	this.list.Remove(e)
}

func (this *LRUCache) PrintList() {
	node := this.list.Front()
	fmt.Print("{")
	for node != nil {
		if node.Next() != nil {
			fmt.Print(node.Value, "->")
		} else {
			fmt.Print(node.Value)
			break
		}
		node = node.Next()
	}
	fmt.Print("}\n")
}
