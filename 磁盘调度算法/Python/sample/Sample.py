from CSCAN import *
from  SSTF import *
from  SCAN import *
from FCFS import *
def caculate(queue):
    print('访问的磁道序列为: ', end='')
    print(queue)
    sum_gap = sum([(abs(queue[i] - queue[i - 1])) for i in range(1, len(queue))])
    print('移动的磁道数为：%d' % sum_gap)
    print('平均移动的磁道数为：%.2f' % (sum_gap / TRACK_REQUEST_COUNT))
    print("")
if __name__ == '__main__':
    track_request = [None] * TRACK_REQUEST_COUNT
    for i1 in range(TRACK_REQUEST_COUNT):
        track_request[i1] = random.randint(0, TRACK_MAX_COUNT)

    print('每次生成的磁道序列是随机的，对于不同的序列算法的算法的性能是不一样的，'
          '通过多次比较观察结果，SSTF是算法中移动的磁道数最少的')

    print("TRACK SEQUECE:    ")
    print(track_request)
    print('')

    print("FCFS:    ")
    caculate(FCFS(track_request))

    print("SSTF:    ")
    caculate(SSTF(track_request))

    print("SCAN:    ")
    caculate(SCAN(track_request))

    print("CSCAN:   ")
    caculate(CSCAN(track_request))