# 丁士钦 2023.05.19 SJF算法
global N
N = 5
pcb = []


# 输入函数
def inPcb():
    global N
    i = 0
    while i < N:
        print("****************************************")
        pName = input("请输入进程名 ：")  # 进程名
        inTime = input("请输入进入时间：")  # 进入时间
        serverTime = input("请输入服务时间：")  # 服务时间
        # 数据按顺序存放到列表中 startTime=开始时间 finishTime=完成时间 zzTime=周转时间 dqzzTime=带权周转时间
        #          进程名 进入时间 服务时间  开始 完成 周转 带权
        pcb.append([pName, inTime, serverTime, 0, 0, 0, 0])
        i = i + 1


# 短作业优先
def sjf():
    sjf_pcb = pcb
    temp_pcb = pcb[1:len(sjf_pcb)]  # 切片 临时存放后备队列  len(sjf_pcb)获取长度。
    temp_pcb.sort(key=lambda x: x[2], reverse=False)  # 对后背队列 按照服务时间排序
    sjf_pcb[1:len(sjf_pcb)] = temp_pcb
    i = 0
    for i in range(N):
        if i == 0:
            startTime = int(sjf_pcb[0][1])
            pcb[0][3] = startTime
            pcb[0][4] = startTime + int(sjf_pcb[0][2])
        else:
            startTime = sjf_pcb[i - 1][4]
            pcb[i][3] = startTime
            pcb[i][4] = startTime + int(sjf_pcb[i][2])
            i = i + 1
    for i in range(N):
        sjf_pcb[i][5] = int(sjf_pcb[i][4]) - int(sjf_pcb[i][1])  # 周转时间
        sjf_pcb[i][6] = float(sjf_pcb[i][5]) / int(sjf_pcb[i][2])  # 带权周转时间
        i = i + 1
    # 输出结果
    pcb.sort(key=lambda x: x[0], reverse=False)  # 升序排序  按照进程名排序
    print('短作业优先SJF运行结果：')
    for i in range(N):
        print("进程名：%s 进入时间：%d 服务时间：%d 开始时间："
              "%d 完成时间：%d 周转时间：%d 带权周转时间：%.2f"
              % (sjf_pcb[i][0], int(sjf_pcb[i][1]), int(sjf_pcb[i][2]), int(sjf_pcb[i][3]),
                 int(sjf_pcb[i][4]), float(sjf_pcb[i][5]), float(sjf_pcb[i][6])))

