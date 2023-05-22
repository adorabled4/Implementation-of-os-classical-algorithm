import random

import FCFS
import model


def init(num):  # 初始化进程，生成四个进程并按到达时间将它们放入列表list1
    list1 = []
    for i in range(num):
        list1.append(model.Process(str(i), random.randint(1, 10), random.randint(10, 15),
                                  random.randint(1, 10), 0, random.randint(5, 10), random.randint(1, 10), "Ready"))
    for i in range(len(list1) - 1):
        for j in range(i + 1, len(list1)):
            if list1[i].arr_time > list1[j].arr_time:
                list1[i], list1[j] = list1[j], list1[i]
    return list1

if __name__ == "__main__":
    list1 = init(4)
    for i in list1:
        i.Output()
    FCFS.fcfs(list1)