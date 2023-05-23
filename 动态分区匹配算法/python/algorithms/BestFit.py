#123
from python.algorithms.FirstFit import *
# 循环首次适应算法
def alloc1(taskID, Tasklength, list):
    global p_sign, p_num, time
    if time == 0:
        p_sign = list[0]
        time = 1
    for i in range(0, len(list)):
        p = list[i]
        if (p.start - 1) == p_sign.end:
            p_num = i
    for i in range(p_num, len(list)):
        p = list[i]
        if p.state == 1 and p.length > Tasklength:
            node2 = node(p.start + Tasklength, p.end, p.length - Tasklength, 1, 0)
            a = node(p.start, p.start + Tasklength - 1, Tasklength, state=0, ID=taskID)
            print("已分配",a.Id, ' :start ', a.start, " end ", a.end, " length ", a.length)
            p_sign = a
            del list[i]
            list.insert(i, node2)
            list.insert(i, a)
            # showList(list)
            return
        if p.state == 1 and p.length == Tasklength:
            print("已分配",taskID, ' :start ', p.start, " end ", p.end, " length ", p.length)
            p.state = 0
            showList(list)
            return
    for i in range(p_num):
        p = list[i]
        if p.state == 1 and p.length > Tasklength:
            node2 = node(p.start + Tasklength, p.end, p.length - Tasklength, 1, 0)
            a = node(p.start, p.start + Tasklength - 1, Tasklength, state=0, ID=taskID)
            p_sign = a
            del list[i]
            list.insert(i, node2)
            list.insert(i, a)
            showList(list)
            return
        if p.state == 1 and p.length == Tasklength:
            p.state = 0
            showList(list)
            return
    print("内存空间不足")
