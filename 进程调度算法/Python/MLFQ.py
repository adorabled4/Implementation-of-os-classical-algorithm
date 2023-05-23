#丁士钦 2023.05.19 MLFQ算法


import heapq


class Process:
    def __init__(self, name, arrive_time, serve_time, priority=0):
        self.name = name  # 进程名
        self.arrive_time = arrive_time  # 进程到达时间
        self.serve_time = serve_time  # 进程服务时间
        self.left_serve_time = serve_time  # 进程剩余服务时间，初始化为服务时间
        self.finish_time = 0  # 进程完成时间，初始化为 0
        self.cycling_time = 0  # 进程周转时间，初始化为 0
        self.w_cycling_time = 0  # 进程带权周转时间，初始化为 0
        self.priority = priority  # 进程优先级，默认为 0

    def __lt__(self, other):
        return self.arrive_time < other.arrive_time  # 比较两个进程到达时间的先后顺序

    def execute(self, time_slice=None):
        if time_slice is None or self.left_serve_time <= time_slice:
            # 进程完成执行
            self.finish_time = max(self.arrive_time + self.serve_time, self.finish_time)
            self.cycling_time = self.finish_time - self.arrive_time
            self.w_cycling_time = self.cycling_time / self.serve_time
            self.left_serve_time = 0
            return True
        else:
            # 进程需要继续执行
            self.left_serve_time -= time_slice
            return False


class PriorityQueue:
    def __init__(self):
        self.heap = []  # 在初始时创建一个空堆
        self.index = 0  # 初始化堆的索引为 0

    def size(self):
        # 返回堆中元素的数量
        return len(self.heap)

    def add(self, priority, item):
        # 将一个元素添加到堆中
        heapq.heappush(self.heap, (priority, self.index, item))
        self.index += 1

    def get(self):
        # 获取堆中优先级最高的元素
        if self.heap:
            return heapq.heappop(self.heap)[-1]
        else:
            return None


class RR:
    def __init__(self, process_dict, time_slice):
        self.process_dict = process_dict
        self.time_slice = time_slice

    def scheduling(self):
        queue = PriorityQueue()
        for process in self.process_dict.values():
            queue.add(process.arrive_time, process)

        total_time = 0
        while queue.size() > 0:
            process = queue.get()
            if not process.execute(self.time_slice):
                # 进程需要继续执行，添加回队列
                queue.add(total_time, process)
            total_time += self.time_slice


class MultilevelFeedbackQueue:
    def __init__(self, queue_list, time_slices):
        self.queue_list = queue_list
        self.time_slices = time_slices

    def scheduling(self):
        for i in range(len(self.queue_list)):
            current_queue = self.queue_list[i]
            time_slice = self.time_slices[i]

            while current_queue.size() > 0:
                process = current_queue.get()
                if not process.execute(time_slice):
                    # 进程需要移动到下一个队列
                    if i == len(self.queue_list) - 1:
                        # 最后一个队列，使用具有指定时间片的RR调度
                        rr = RR({process.name: process}, time_slice)
                        rr.scheduling()
                    else:
                        # 将进程添加到下一个队列的末尾
                        self.queue_list[i + 1].add(process.priority, process)

                else:
                    # 进程已完成执行，从队列中删除
                    current_queue.get()

