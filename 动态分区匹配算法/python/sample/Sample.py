
from python.algorithms.FirstFit import *
from python.algorithms.BestFit import *
from python.algorithms.QuickFit import *
from python.algorithms.WorstFit import *
if __name__ == "__main__":
    while True:
        try:
            x = int(input("请选择：0首次适应算法，1循环首次适应算法，2最佳适应算法，3最坏适应算法,4退出程序"))
            a = node(0, 639, 640, state=1, ID=0)
            b = []
            b.append(a)
            if x == 0:
                alloc0(1, 130, b)
                alloc0(2, 60, b)
                alloc0(3, 100, b)
                showList2(b)
                free_k(2, b)
                alloc0(4, 200, b)
                showList2(b)
                free_k(3, b)
                free_k(1, b)
                showList2(b)
                alloc0(5, 140, b)
                alloc0(6, 60, b)
                alloc0(7, 50, b)
                free_k(6, b)
                showList2(b)
            elif x == 1:
                alloc1(1, 130, b)
                alloc1(2, 60, b)
                alloc1(3, 100, b)
                free_k(2, b)
                alloc1(4, 200, b)
                free_k(3, b)
                free_k(1, b)
                alloc1(5, 140, b)
                alloc1(6, 60, b)
                alloc1(7, 50, b)
                free_k(6, b)
                showList2(b)
            elif x == 2:
                alloc2(1, 130, b)
                alloc2(2, 60, b)
                alloc2(3, 100, b)
                free_k(2, b)
                alloc2(4, 200, b)
                free_k(3, b)
                free_k(1, b)
                alloc2(5, 140, b)
                alloc2(6, 60, b)
                alloc2(7, 50, b)
                free_k(6, b)
                showList2(b)
            elif x == 3:
                alloc3(1, 130, b)
                alloc3(2, 60, b)
                alloc3(3, 100, b)
                free_k(2, b)
                alloc3(4, 200, b)
                free_k(3, b)
                free_k(1, b)
                alloc3(5, 140, b)
                alloc3(6, 60, b)
                alloc3(7, 50, b)
                free_k(6, b)
                showList2(b)
            elif x == 4:
                break
            else:
                print("请输入正确的选项")
                continue
        except:
            print("请输入正确的选项")
