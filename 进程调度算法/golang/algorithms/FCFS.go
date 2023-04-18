package algorithms

import (
	"fmt"
	"golang/model"
	"time"
)

type FCFS struct {
	cap     int // 容量
	Ready   []model.Process
	block   []model.Process
	running *model.Process
}

// Constructor 构造器
func FcFsConstructor(capacity int) *FCFS {
	return &FCFS{
		cap:     capacity,
		Ready:   []model.Process{},
		block:   []model.Process{},
		running: nil,
	}
}

func (this *FCFS) Add(process model.Process) {
	fmt.Println(getNowTime()+"\033[35m[Arrive]\033[0m进程到达 , 进程ID :", process.Pid, " 预计用时: ", process.RunTime, "(s)")
	process.Status = model.READY
	if len(this.Ready) >= this.cap {
		process.Status = model.BLOCK
		this.block = append(this.block, process)
	} else {
		this.Ready = append(this.Ready, process)
	}
}

func (this *FCFS) Start() {
	for {
		// 每次间歇
		time.Sleep(time.Duration(100) * time.Millisecond)
		if len(this.Ready)+len(this.block) > 0 {
			this.execute()
		}
	}
}

func (this *FCFS) execute() {
	for this.running != nil || len(this.Ready)+len(this.block) > 0 {
		if len(this.Ready) < 1 {
			this.fromBlock2Ready()
		}
		// 判断当前进程是否存在
		if this.running == nil {
			this.running = &this.Ready[0]
			this.Ready = this.Ready[1:]
			time.Sleep(time.Second * time.Duration(this.running.RunTime))
			fmt.Println(getNowTime()+"\033[32m[finish]\033[0m执行完毕 , 进程ID :", this.running.Pid, " 耗时: ", this.running.RunTime, "(s)")
		} else {
			time.Sleep(time.Second * time.Duration(this.running.RunTime))
			fmt.Println(getNowTime()+"\033[32m[finish]\033[0m执行完毕 , 进程ID :", this.running.Pid, " 耗时: ", this.running.RunTime, "(s)")
		}
		this.running = nil
	}
}

// 把程序从阻塞队列移动到等待队列
func (this *FCFS) fromBlock2Ready() {
	for len(this.Ready) < this.cap && len(this.block) > 0 {
		process := this.block[len(this.block)-1]
		process.Status = model.READY
		this.Ready = append(this.Ready, process)
		this.block = this.block[:len(this.block)-1] // 利用切片
	}
}

func getNowTime() string {
	return time.Now().Format("2006-01-02 15:04:05") + "  "
}
