import copy

p_sign = None
p_num = 0
time = 0

class node(object):
    def __init__(self, start, end, length, state=1, ID=0):
        self.start = start
        self.end = end
        self.length = length
        self.state = state  ##state为1：内存未分配
        self.Id = ID  ##ID为0是未分配，其余为任务编号

def showList(list):
    """展示空闲分区"""
    print("空闲分区如下")
    id = 1
    for i in range(0, len(list)):
        p = list[i]
        if p.state == 1:
            print(id, ' :start ', p.start, " end ", p.end, " length ", p.length)
            id += 1

def showList2(list):
    """展示已分配分区"""
    print("已分配分区如下")
    for i in range(0, len(list)):
        p = list[i]
        if p.state == 0:
            print(p.Id, ' :start ', p.start, " end ", p.end, " length ", p.length)

def free_k(taskID, li):
    for i in range(0, len(li)):
        p = li[i]
        if p.Id == taskID:
            p.state = 1
            x = i
            print("已释放", taskID, ' :start ', p.start, " end ", p.end, " length ", p.length)
            break

    # 向前合并空闲块
    if x - 1 > 0:
        if li[x - 1].state == 1:
            a = node(li[x - 1].start, li[x].end, li[x - 1].length + li[x].length, 1, 0)
            del li[x - 1]
            del li[x - 1]
            li.insert(x - 1, a)
            x = x - 1
    # 向后合并空闲块
    if x + 1 < len(li):
        if li[x + 1].state == 1:
            a = node(li[x].start, li[x + 1].end, li[x].length + li[x + 1].length, 1, 0)
            del li[x]
            del li[x]
            li.insert(x, a)
    showList(li)

# 首次适应算法
def alloc0(taskID, Tasklength, list):
    for i in range(0, len(list)):
        p = list[i]
        if p.state == 1 and p.length > Tasklength:
            node2 = node(p.start + Tasklength, p.end, p.length - Tasklength, 1, 0)
            a = node(p.start, p.start + Tasklength - 1, Tasklength, state=0, ID=taskID)
            print("已分配",a.Id, ' :start ', a.start, " end ", a.end, " length ", a.length)
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
    print("内存空间不足")







