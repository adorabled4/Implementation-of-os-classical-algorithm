# 丁士钦 2023.05.19 SRTF算法测试


import SRTF

processes = [
    SRTF.Process(1, 0, 8),
    SRTF.Process(2, 1, 4),
    SRTF.Process(3, 2, 9),
    SRTF.Process(4, 3, 5),
    SRTF.Process(5, 4, 2)
]
SRTF.SRTF(processes)
