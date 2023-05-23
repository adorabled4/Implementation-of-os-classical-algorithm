#123
from algorithms.FirstFit import *
##最佳适应算法
def bubble_sort(list):
    # 冒泡排序
    count = len(list)
    for i in range(0, count):
        for j in range(i + 1, count):
            if list[i].length < list[j].length:
                list[i], list[j] = list[j], list[i]
    return list
def alloc2(taskID, Tasklength, li):
    q = copy.copy(li)
    q = bubble_sort(q)
    s = -1
    ss12 = -1
    for i in range(0, len(q)):
        p = q[i]
        if p.state == 1 and p.length > Tasklength:
            s = p.start
        elif p.state == 1 and p.length == Tasklength:
            ss12 = p.start
    if s == -1 and ss12 == -1:
        print("内存空间不足")
        return
    for i in range(0, len(li)):
        p = li[i]
        if p.start == s:
            node2 = node(p.start + Tasklength, p.end, p.length - Tasklength, 1, 0)
            a = node(p.start, p.start + Tasklength - 1, Tasklength, state=0, ID=taskID)
            print("已分配",a.Id, ' :start ', a.start, " end ", a.end, " length ", a.length)
            del li[i]
            li.insert(i, node2)
            li.insert(i, a)
            # showList(li)
            return
        elif p.start == ss12:
            print("已分配",taskID, ' :start ', p.start, " end ", p.end, " length ", p.length)
            p.state = 0
            showList(li)
            return
