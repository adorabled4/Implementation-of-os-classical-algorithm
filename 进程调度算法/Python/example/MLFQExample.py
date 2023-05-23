# 丁士钦 2023.05.19 MLFQ算法测试
import MLFQ

if __name__ == '__main__':
    # 产生进程
    process_dict = {
        'A': MLFQ.Process('A', 0, 4, 1),
        'B': MLFQ.Process('B', 1, 3, 2),
        'C': MLFQ.Process('C', 2, 4, 1),
        'D': MLFQ.Process('D', 3, 2, 2),
        'E': MLFQ.Process('E', 4, 4, 1)
    }

    # 使用RR调度算法，时间片为1
    print('---------------------------轮转调度算法--------------------------')
    rr = MLFQ.RR(process_dict, 1)
    rr.scheduling()

    # 使用多级反馈队列调度算法
    print()
    print('-----------------------测试多级反馈队列调度算法----------------------')
    queue_list = [MLFQ.PriorityQueue(), MLFQ.PriorityQueue(), MLFQ.PriorityQueue()]
    time_slices = [1, 2, 4]

    for process in process_dict.values():
        queue_list[0].add(process.priority, process)

    mlfq = MLFQ.MultilevelFeedbackQueue(queue_list, time_slices)
    mlfq.scheduling()

    # 输出结果
    for name, process in process_dict.items():
        print("Process {}:".format(name))
        print("\tArrive time: {}".format(process.arrive_time))
        print("\tServe time: {}".format(process.serve_time))
        print("\tFinish time: {}".format(process.finish_time))
        print("\tCycling time: {}".format(process.cycling_time))
        print("\tWeighted cycling time: {}".format(process.w_cycling_time))
