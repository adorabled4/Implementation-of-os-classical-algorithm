from typing import List

from example.FCFSExample import init


class PCB:
    def __init__(self, name: str, arrive_time: int, service_time: int, weight: int):
        self.name = name
        self.arrive_time = arrive_time
        self.service_time = service_time
        self.weight = weight
        self.r_service_time = service_time
        self.state = 'W'  # W: waiting, R: running, F: finished
        self.id = 0
        self.finish_time = 0
        self.run_time = 0
        self.avg_time = 0

    def is_waiting(self, current_time: int) -> bool:
        return self.r_service_time > 0 and self.arrive_time <= current_time


class PS:
    def __init__(self):
        self.n = 0
        self.ps_array: List[PCB] = []
        self.current_time = 0
        self.wait_list: List[PCB] = []

    def init(self) -> None:
        print("请输入进程数(>3)：")
        self.n = int(input().strip())
        for i in range(self.n):
            print(f"请依次输入第{i + 1}个进程的姓名，进程到达时间，进程服务时间，进程的优先级：")
            name, arrive_time, service_time, weight = input().split()
            pcb = PCB(name, int(arrive_time), int(service_time), int(weight))
            pcb.id = i
            self.ps_array.append(pcb)
        self.ps_array.sort(key=lambda x: x.arrive_time)

    # 显示每个时刻的进程情况
    def show(self) -> None:
        print("当前时刻\t进程名\t剩余服务时间\t优先级\t进程状态")
        for pcb in self.ps_array:
            print(f"{self.current_time}--{self.current_time + 1}\t{pcb.name}\t{pcb.r_service_time}\t\t{pcb.weight}\t{pcb.state}")
        print("--------------------------------------------------------------")

    # 打印最终结果，周转时间和带权周转时间
    def print_result(self) -> None:
        # 计算周转时间
        for pcb in self.ps_array:
            pcb.run_time = pcb.finish_time - pcb.arrive_time
            pcb.avg_time = pcb.run_time / pcb.service_time

        print("进程名\t结束时间\t周转时间\t带权周转时间")
        for pcb in self.ps_array:
            print(f"{pcb.name}\t{pcb.finish_time}\t\t{pcb.run_time}\t\t{pcb.avg_time}")

    # 运行
    def run(self) -> None:
        self.current_time = 0  # 记录当前时刻
        # 已完成的进程数，初始化为0
        finished_count = self.n
        while finished_count != 0:
            # 判断当前时刻到达的进程，并将它添加到容器wait_list中
            self.wait_list = [pcb for pcb in self.ps_array if pcb.is_waiting(self.current_time)]

            tmp = PCB('', 0, 0, -100)
            index = 0
            # 执行当前等待进程中优先级最高的那一个
            for i, pcb in enumerate(self.wait_list):
                if pcb.weight > tmp.weight:
                    tmp = pcb
                    index = i

            # 更新所有进程的状态
            for i, pcb in enumerate(self.ps_array):
                if pcb.is_waiting(self.current_time):
                    self.ps_array[i].state = 'W'
                    if tmp.id == pcb.id:
                        self.ps_array[i].state = 'R'
                        self.ps_array[i].weight -= 1
                        self.ps_array[i].r_service_time -= 1
                        self.show()
                        if self.ps_array[i].r_service_time == 0:
                            self.ps_array[i].state = 'F'
                            finished_count -= 1
                            self.ps_array[i].finish_time = self.current_time + 1

            # 更新当前时刻
            self.current_time += 1


