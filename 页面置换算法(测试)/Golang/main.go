package main

import (
	"fmt"
	"golang/lru"
)

func main() {
	LruTest()
}
func LruTest() {
	cache := lru.Constructor(3)
	cache.Put(1, 1)
	cache.Put(2, 2)
	cache.Put(3, 3)
	fmt.Print("初始化lru队列 : ")
	cache.PrintList() // 打印初始的lru队列
	cache.Put(4, 4)   // 添加 node4 ,由于容量有限, node1被移除
	fmt.Print("添加node(4,4) : ")
	cache.PrintList()
	fmt.Print("调用node(2,2) : ")
	cache.Get(2) // 再次调用, node2重新回到最新的位置
	cache.PrintList()
}
