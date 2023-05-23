package main

import (
	"fmt"
	"golang/algorithms"
	"golang/model"
	"math/rand"
	"time"
)

var fcfs *algorithms.FCFS
var basePid = 1000

func main() {
	FCFSSample()
}

func FCFSSample() {
	fcfs = algorithms.FcFsConstructor(5)
	go addProcess(5, 6, 4) // 开启新的线程
	fcfs.Start()
}

// 添加进程
func addProcess(size, maxRunTime, maxSleepTime int) {
	for i := 0; i < size; i++ {
		runtime := rand.Intn(maxRunTime)
		process := model.Constructor(basePid+i, runtime)
		if fcfs == nil {
			fmt.Println("fcfs is nil")
			i = i - 1
		} else {
			fcfs.Add(process)
		}
		time.Sleep(time.Duration(rand.Intn(maxSleepTime)) * time.Second)
	}
}

/*
下面是一组示例 , 可以看到执行的顺序完全符合进程的到达顺序
2023-04-18 10:11:05  [Arrive]进程到达 , 进程ID : 1000  预计用时:  5 (s)
2023-04-18 10:11:08  [Arrive]进程到达 , 进程ID : 1001  预计用时:  5 (s)
2023-04-18 10:11:10  [finish]执行完毕 , 进程ID : 1000  耗时:  5 (s)
2023-04-18 10:11:11  [Arrive]进程到达 , 进程ID : 1002  预计用时:  1 (s)
2023-04-18 10:11:13  [Arrive]进程到达 , 进程ID : 1003  预计用时:  1 (s)
2023-04-18 10:11:13  [Arrive]进程到达 , 进程ID : 1004  预计用时:  4 (s)
2023-04-18 10:11:15  [finish]执行完毕 , 进程ID : 1001  耗时:  5 (s)
2023-04-18 10:11:16  [finish]执行完毕 , 进程ID : 1002  耗时:  1 (s)
2023-04-18 10:11:17  [finish]执行完毕 , 进程ID : 1003  耗时:  1 (s)
2023-04-18 10:11:21  [finish]执行完毕 , 进程ID : 1004  耗时:  4 (s)
*/
