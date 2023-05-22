class Process:
    def __init__(self, pid, priority, arr_time, all_time, cpu_time, start_block, block_time, state):  ##初始化进程
        self.pid = pid  # 进程id
        self.priority = priority  # 进程的优先级，用于确定首先执行的进程。
        self.arr_time = arr_time  # 到达时间，进程到达就绪队列的时间。
        self.all_time = all_time  # 分配的时间，分配给流程的总时间量
        self.cpu_time = cpu_time  # CPU 时间，进程已经在 CPU 上花费的时间。
        self.start_block = start_block  # 启动块，分配给进程的启动内存块
        self.block_time = block_time  # 阻塞时间，进程被阻止的时间。
        self.state = state  # 进程的当前状态

    def Output(self):  # fcfs输出
        print("进程" + str(self.pid), "正在执行，到达时间:" + str(self.arr_time),
              "还需运行时间:" + str(self.all_time), "已运行时间:" + str(self.cpu_time))

    def toBlock(self):  # 将状态置为Block
        self.state = "Block"

    def toRun(self):  # 将状态置为Run
        self.state = "Run"

    def toFinish(self):  # 将状态置为Finish
        self.state = "Finish"

    def toReady(self):  # 将状态置为Ready
        self.state = "Ready"

    def running(self):  ##进程运行时状态变化
        self.all_time -= 1
        self.cpu_time += 1

    def toBlocking(self):  ##进程将要开始阻塞的状态变化
        if self.start_block > 0:
            self.start_block -= 1

    def blocking(self):  ##进程阻塞时的状态变化
        if self.block_time > 0:
            self.block_time -= 1
        self.priority += 1
