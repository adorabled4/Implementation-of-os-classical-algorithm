import random
#123
import copy
TRACK_MAX_COUNT = 100  # 磁道的总数
TRACK_REQUEST_COUNT = 10  # 请求访问的磁道数量
TRACK_START = 50
SCAN_DIRECTION = 1  # 1表示向磁道号增加的方向扫描，0表示向磁道号减小的方向
N_STEPSCAN = 4  # 表示请求队列被划分为长度为 N 的子队列
def FCFS(track_request):
    queue_FCFS = track_request.copy()
    queue_FCFS.insert(0, TRACK_START)
    return queue_FCFS


