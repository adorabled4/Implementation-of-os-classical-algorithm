
def fcfs(list1):  # 先来先服务
    time = 0
    while 1:
        print("time:", time)
        if time >= list1[0].arr_time:
            list1[0].running()
            list1[0].Output()
            if list1[0].all_time == 0:
                print("进程" + list1[0].pid + " 执行完毕,周转时间：" + str(time - list1[0].arr_time + 1) + "\n")
                list1.remove(list1[0])
        time += 1
        if not list1:
            break


