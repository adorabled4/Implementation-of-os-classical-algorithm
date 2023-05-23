from FCFS import *
def CSCAN(track_request):
    global SCAN_DIRECTION
    queue_CSCAN = []
    queue_CSCAN.append(TRACK_START)
    track_request_copy = copy.deepcopy(track_request)
    track_request_copy.sort()
    i = 0
    is_find = False
#
    if SCAN_DIRECTION == 1:
        while i < track_request_copy.__len__():
            if (TRACK_START <= track_request_copy[i]) & (is_find == False):
                index = i
                i = 0
                is_find = True
            if is_find == True:
                queue_CSCAN.append(track_request_copy[index % TRACK_REQUEST_COUNT])
                index += 1
            i += 1

    if SCAN_DIRECTION == 0:
        track_request_copy.reverse()
        while i < track_request_copy.__len__():
            if (TRACK_START >= track_request_copy[i]) & (is_find == False):
                index = i
                i = 0
                is_find = True
            if is_find == True:
                queue_CSCAN.append(track_request_copy[index % TRACK_REQUEST_COUNT])
                index += 1
                current = track_request_copy[index % TRACK_REQUEST_COUNT]
            i += 1

    return queue_CSCAN

