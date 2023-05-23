from FCFS import *

def findNearest(current, track_request, visited):
    minDis = TRACK_MAX_COUNT
    minIndex = -1
    for i in range(len(track_request)):
        if visited[i] == False:
            dis = abs(current - track_request[i])
            if dis < minDis:
                minDis = dis
                minIndex = i
    visited[minIndex] = True
    return minIndex, minDis


def SSTF(track_request):
    visited = [False] * TRACK_REQUEST_COUNT
    queue_FCFS = []
    current = TRACK_START  # 起始的磁道
    for i in range(len(track_request) + 1):
        index, dis = findNearest(current, track_request, visited)
        queue_FCFS.append(current)
        current = track_request[index]
    return queue_FCFS

    queue_SCAN.append(TRACK_START)
    current = TRACK_START

