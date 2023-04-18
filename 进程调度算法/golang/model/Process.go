package model

import (
	"time"
)

// 进程状态枚举 : https://zhuanlan.zhihu.com/p/427532561
const (
	BLOCK = iota
	READY
)

type Process struct {
	Pid          int        // 进程id
	Status       int        // 进程状态
	Order        int        // 进程优先级
	RunTime      int        // 进程执行时间
	arriveTime   *time.Time // 进程到达时间
	FinishTime   *time.Time // 进程完成时间
	cycleTime    int        // 进程周转时间
	aveCycleTime int        // 平均周转时间
	Preemption   float64    // HRRN 算法的优先权
}

func Constructor(pid, runTime int) Process {
	now := time.Now()
	return Process{
		Pid:          pid,
		Status:       0,
		Order:        0,
		RunTime:      runTime,
		arriveTime:   &now,
		cycleTime:    0,
		aveCycleTime: 0,
		Preemption:   0,
	}
}

func ConstructorOrder(pid, runTime, order int) Process {
	now := time.Now()
	return Process{
		Pid:          pid,
		Status:       0,
		Order:        order,
		RunTime:      runTime,
		arriveTime:   &now,
		cycleTime:    0,
		aveCycleTime: 0,
		Preemption:   0,
	}
}
