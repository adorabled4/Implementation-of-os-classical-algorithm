#丁士钦 2023.05.19 RR算法测试

import RR


def main():
    while True:
        p_num = input("请输入进程数：")
        if p_num.isdigit():
            p_num = int(p_num)
            p_dict = RR.createP(p_num)
            RR.lunzhuanP(p_dict, p_num)

        else:
            print("输入的不是有效数字")


if __name__ == "__main__":
    main()
