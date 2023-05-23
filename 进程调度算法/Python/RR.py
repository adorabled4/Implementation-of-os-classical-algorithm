# 丁士钦 2023.05.19 RR算法

import random


def createP(p_num):  # 创建字典，根据输入的进程数进行创建进程字典，时间为随机1-9s之间
    p_dict = {}
    # 获取一个a-z的列表
    alphabet_list = list(map(chr, range(ord('a'), ord('z') + 1)))

    for i in range(0, p_num):
        # 默认进程名为a-z顺序排序
        p_dict[alphabet_list[i]] = random.randint(1, 9)
    return p_dict


def lunzhuanP(p_dict, p_num):  # 开始轮转
    print("进程名称" + " " * 10 + "每个进程需要工作的时间")
    p_time = {}
    p_list = [[-1 for i in range(4)] for j in range(p_num + 1)]
    # 添加输出头
    p_list[0][0] = "Name"
    p_list[0][1] = "run"
    p_list[0][2] = "req"
    p_list[0][3] = "status"
    z = 1
    for k, v in p_dict.items():
        # 将进程初始化设置初始值
        p_list[z][0] = k
        p_list[z][1] = 0
        p_list[z][2] = v
        p_list[z][3] = "R"
        p_time[k] = 0
        z += 1

    for k, v in p_dict.items():
        print(k + "          ", v)
    j = 1
    t = 1
    while True:
        if len(p_list) == 1:
            print("进程执行完毕")
            for k, v in p_time.items():
                print("进程%s的执行周期为：%s" % (k, v))
            break

        # 判断是否有进程执行完毕
        for y in range(1, len(p_list) - 1):

            if p_list[y][3] == "E":
                del p_list[y]

        if j <= len(p_list) - 1:

            if p_list[j][1] + 1 <= p_list[j][2]:

                p_list[j][1] += 1

                if p_list[j][1] == p_list[j][2]:
                    p_list[j][3] = "E"
            else:

                del p_list[j]
                continue

        else:
            j = j - len(p_list) + 1
            continue

        print("cpu时刻: ", t)
        print("正在执行的进程：", p_list[j][0])
        p_time[p_list[j][0]] += 1
        j += 1
        t += 1
        for i in range(len(p_list)):
            print(p_list[i])
