# 丁士钦 2023.05.19 

import heapq


class Process:
    def __init__(self, pid, arrival_time, burst_time):
        self.pid = pid
        self.arrival_time = arrival_time
        self.burst_time = burst_time
        self.remaining_time = burst_time

    def __lt__(self, other):
        # 用于优先队列排序，按照剩余时间从小到大排序
        return self.remaining_time < other.remaining_time


def SRTF(processes):
    n = len(processes)
    remaining_processes = processes.copy()
    current_time = 0
    completed_processes = []
    ready_queue = []  # 优先队列，存储可运行进程
    heapq.heapify(ready_queue)  # 初始化为空堆
    while remaining_processes or ready_queue:
        # 将到达时间小于等于当前时间的进程加入就绪队列
        for process in remaining_processes:
            if process.arrival_time <= current_time:
                heapq.heappush(ready_queue, process)
                remaining_processes.remove(process)

        if not ready_queue:  # 没有可运行的进程
            current_time += 1
            continue

        # 执行剩余时间最短的进程
        shortest_process = heapq.heappop(ready_queue)
        shortest_process.remaining_time -= 1
        print(f"时间 {current_time}: 进程 {shortest_process.pid} 正在运行")
        current_time += 1

        if shortest_process.remaining_time == 0:  # 进程完成
            shortest_process.finish_time = current_time
            completed_processes.append(shortest_process)
        else:  # 如果进程未完成，则将其重新加入就绪队列
            heapq.heappush(ready_queue, shortest_process)

    # 输出调度结果
    print("SRTF算法调度结果:")
    total_turnaround_time = 0
    total_waiting_time = 0
    for process in completed_processes:
        turnaround_time = process.finish_time - process.arrival_time
        waiting_time = turnaround_time - process.burst_time
        total_turnaround_time += turnaround_time
        total_waiting_time += waiting_time
        print(f"进程 {process.pid}: 周转时间={turnaround_time}, 等待时间={waiting_time}")
    print(f"平均周转时间: {total_turnaround_time / n:.2f}")
    print(f"平均等待时间: {total_waiting_time / n:.2f}")

