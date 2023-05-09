//
// Created by 刘晋昂 on 2023/5/8.
//
#include "../algorithms/SLF.cpp"
int main()
{
    system("chcp 65001");
    // 初始化就绪队列
    init();
    // 按到达时间从小到大排序
    sort(SLFobject.SLFarray + 1, SLFobject.SLFarray + 1 + SLFobject.n,
         [](const PCB & x, const PCB & y)
         { return x.arriveTime < y.arriveTime; });
    // 显示进程初始状态
    show();
    // 实际运行阶段
    run();
    // 显示各进程的周转时间和带权周转时间
    display();
    system("pause");
    return 0;
}
