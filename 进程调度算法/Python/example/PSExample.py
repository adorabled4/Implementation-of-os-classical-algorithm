# 丁士钦 2023.05.19 PS算法测试

import PS


if __name__ == "__main__":
    # 创建PS对象
    ps = PS.PS()

    # 初始化进程
    ps.init()

    # 运行进程调度
    ps.run()

    # 打印最终结果
    ps.print_result()