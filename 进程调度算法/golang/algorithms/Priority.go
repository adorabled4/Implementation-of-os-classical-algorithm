package algorithms

import (
	"fmt"
	. "golang/model"
	"time"
)

type Priority struct {
	cap     int
	Ready   ProcessHeap
	Block   []Process
	Running *Process
}

// Constructor 构造器
func PriorityConstructor(capacity int) *Priority {
	return &Priority{
		cap:     capacity,
		Ready:   ProcessHeap{},
		Block:   []Process{},
		Running: nil,
	}
}

func (algo *Priority) Add(process Process) {
	fmt.Println(getNowTime()+"\033[35m[Arrive]\033[0m进程到达 , 进程ID :", process.Pid, "进程优先级: ", process.Order, " 预计用时: ", process.RunTime, "(s)")
	process.Status = READY
	if algo.Ready.Len() >= algo.cap {
		process.Status = BLOCK
		algo.Block = append(algo.Block, process)
	} else {
		algo.Ready.Push(process)
	}
}

func (algo *Priority) Start() {
	for {
		// 每次间歇
		time.Sleep(time.Duration(100) * time.Millisecond)
		if algo.Ready.Len()+len(algo.Block) > 0 {
			algo.execute()
		}
	}
}

func (algo *Priority) execute() {
	for algo.Running != nil || algo.Ready.Len()+len(algo.Block) > 0 {
		if algo.Ready.Len() < 1 {
			algo.fromBlock2Ready()
		}
		// 判断当前进程是否存在
		if algo.Running == nil {
			//algo.Running = &algo.Ready.Pop().(Process) 不能获取interface{}的地址
			run, _ := algo.Ready.Pop().(Process)
			algo.Running = &run
			time.Sleep(time.Second * time.Duration(algo.Running.RunTime))
			fmt.Println(getNowTime()+"\033[32m[finish]\033[0m执行完毕 , 进程ID :", algo.Running.Pid, " 耗时: ", algo.Running.RunTime, "(s)")
		} else {
			time.Sleep(time.Second * time.Duration(algo.Running.RunTime))
			fmt.Println(getNowTime()+"\033[32m[finish]\033[0m执行完毕 , 进程ID :", algo.Running.Pid, " 耗时: ", algo.Running.RunTime, "(s)")
		}
		algo.Running = nil
	}
}

// 把程序从阻塞队列移动到等待队列
func (algo *Priority) fromBlock2Ready() {
	for algo.Ready.Len() < algo.cap && len(algo.Block) > 0 {
		process := algo.Block[len(algo.Block)-1]
		process.Status = READY
		//algo.Ready = append(algo.Ready, process)
		algo.Ready.Push(process)
		algo.Block = algo.Block[:len(algo.Block)-1] // 利用切片
		algo.Block = algo.Block[:len(algo.Block)-1] // 利用切片
	}
}

// ProcessHeap 实现优先队列
type ProcessHeap []Process

func (h ProcessHeap) Len() int { // 重写 Len() 方法
	return len(h)
}

func (h ProcessHeap) Swap(i, j int) { // 重写 Swap() 方法
	h[i], h[j] = h[j], h[i]
}

func (h ProcessHeap) Less(i, j int) bool { // 重写 Less() 方法， 从小到大排序
	return h[i].Order > h[j].Order
}

func (h *ProcessHeap) Push(process interface{}) {
	*h = append(*h, process.(Process))
}

func (h *ProcessHeap) Pop() interface{} {
	old := *h
	n := len(old)
	x := old[n-1]
	*h = old[0 : n-1]
	return x
}
