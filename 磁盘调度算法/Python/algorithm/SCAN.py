from FCFS import *
#123
def SCAN(track_request):
    global SCAN_DIRECTION
    queue_SCAN = []

    track_request_copy = copy.deepcopy(track_request)
    track_request_copy.sort()
    while track_request_copy.__len__() != 0:
        if SCAN_DIRECTION == 1:
            for track in track_request_copy.copy():
                if TRACK_START <= track:
                    queue_SCAN.append(track)
                    track_request_copy.remove(track)
            SCAN_DIRECTION = 0

        if SCAN_DIRECTION == 0:
            track_request_copy.reverse()
            for track in track_request_copy.copy():
                if TRACK_START >= track:
                    queue_SCAN.append(track)
                    current = track
                    track_request_copy.remove(track)
            SCAN_DIRECTION = 1
    return queue_SCAN
