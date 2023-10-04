package main

import (
	"fmt"
	"golang/algorithms"
	"golang/model"
	"math/rand"
	"time"
)

// 继承调度类
type Dispatch interface {
	Add(process model.Process)
	Start()
}

var basePid = 1000

func main() {
	PrioritySample()
}

func PrioritySample() {
	var prio Dispatch = algorithms.PriorityConstructor(5)
	go addProcess(prio, 5, 6, 4) // 开启新的线程
	prio.Start()
}

func FCFSSample() {
	var fcfs Dispatch = algorithms.FcFsConstructor(5)
	go addProcess(fcfs, 5, 6, 4) // 开启新的线程
	fcfs.Start()
}

// 添加进程
func addProcess(dispatch Dispatch, size, maxRunTime, maxSleepTime int) {
	for i := 0; i < size; i++ {
		runtime := rand.Int()%5 + 2
		order := rand.Int()%5 + 1
		process := model.ConstructorOrder(basePid+i, runtime, order)
		if dispatch == nil {
			fmt.Println("fcfs is nil")
			i = i - 1
		} else {
			dispatch.Add(process)
		}
		time.Sleep(time.Duration(rand.Intn(maxSleepTime)) * time.Second)
	}
}
