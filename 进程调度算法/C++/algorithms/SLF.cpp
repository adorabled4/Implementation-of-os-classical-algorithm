//
// Created by 刘晋昂 on 2023/5/8.
//


#include "../model/PCB.h"

//初始化进程数
void init() {
    cout << "请输入需要调度的进程数： ";
    cin >> SLFobject.n;
    for (int i = 1; i <= SLFobject.n; ++i) {
        cout << "请输入进程" << i << "的名称、到达时间、服务时间： ";
        cin >> SLFobject.SLFarray[i].Name >> SLFobject.SLFarray[i].arriveTime >> SLFobject.SLFarray[i].rserviceTime;
        // 进程状态
        SLFobject.SLFarray[i].State = W;
        // 结束时间
        SLFobject.SLFarray[i].endTime = -1;
        // 剩余服务时间
        SLFobject.SLFarray[i].serviceTime = SLFobject.SLFarray[i].rserviceTime;
        // 周转时间
        SLFobject.SLFarray[i].RunTime = -1;
    }
}
//展示创建的进程及相关信息
void show() {
    cout << "进程初始状态：" << endl;
    cout << "     进程名    " << "    到达时间    " << "    服务时间    " << "    当前状态    " << endl;
    for (int i = 1; i <=  SLFobject.n; ++i) {
        cout << setw(12) << SLFobject.SLFarray[i].Name << setw(10)
             << SLFobject.SLFarray[i].arriveTime << setw(16) << SLFobject.SLFarray[i].serviceTime
             << setw(21) << SLFobject.SLFarray[i].State << endl;
    }
    cout << endl;
}
//短作业优先
bool SJFsort(const PCB &x, const PCB &y)//传入两个进程x,y
{
    // 服务时间不同，短作业优先
    if (x.serviceTime != y.serviceTime)
        return x.serviceTime < y.serviceTime;
        // 服务时间相同，先来先服务
    else
        return x.arriveTime < y.arriveTime;
}
//算出现在arriveTime小于t的进程数
int countWork(int t)
{
    int cnt = 0;
    for (int i = 1; i <= SLFobject.n; ++i) {
        if (SLFobject.SLFarray[i].arriveTime <= t)
            ++cnt;
    }
    return cnt;
}
//更新数据
void updateWork(int num, int cTime)//num剩下进程个数
{
    // 记录剩余时间最短的进程的下标
    int index = 0;
    for (int i = 1; i <= num; ++i) {
        // 进程已经结束
        if (SLFobject.SLFarray[i].serviceTime == 0)
            SLFobject.SLFarray[i].State = F;	// 状态设置为Finished
            // 进程还没结束
        else if (SLFobject.SLFarray[i].serviceTime > 0)
        {
            // 剩余服务时间-1
            --SLFobject.SLFarray[i].serviceTime;
            // 状态设置为Running
            SLFobject.SLFarray[i].State = R;
            index = i;
            break;
        }
    }

    // 更新各进程状态
    for (int i = 1; i <= SLFobject.n; ++i) {
        if (i != index) {
            // 对于不处于Finshed状态的进程
            if (SLFobject.SLFarray[i].State != F)
                // 状态设置为Waiting
                SLFobject.SLFarray[i].State = W;
        }
        // 记录刚完成的进程的结束时间
        if (SLFobject.SLFarray[i].State == F && SLFobject.SLFarray[i].endTime == -1)
            SLFobject.SLFarray[i].endTime = cTime;
    }
}
bool judgeOver()
{
    for (int i = 1; i <= SLFobject.n; ++i)
        if (SLFobject.SLFarray[i].State != F)
            return false;
    return true;
}
void run() {
    cout << "进程调度情况如下：" << endl;
    // 已到达的进程数
    int arriveNum = 0;
    // 当前时间
    int currentTime = 0;
    // 进程是否全部完成标志
    bool isOver = false;
    while (!isOver)
    {
        cout << "   时刻    " << "作业名        " << "    到达时间    " << "  剩余服务时间    " << "    当前状态    " << endl;
        // 计算该时刻已到达的进程数
        arriveNum = countWork(currentTime);
        // 所有已经到达的进程再次排序
        sort(SLFobject.SLFarray + 1, SLFobject.SLFarray + 1 + arriveNum, SJFsort);
        // 更新各进程状态
        updateWork(arriveNum, currentTime);

        // 输出此时刻已到达的各进程的信息
        for (int i = 1; i <= arriveNum; i++)
        {
            cout << setw(6) << currentTime << setw(13) << SLFobject.SLFarray[i].Name
                 << setw(15) << SLFobject.SLFarray[i].arriveTime << setw(15) << SLFobject.SLFarray[i].serviceTime
                 << setw(24) << SLFobject.SLFarray[i].State << endl;
        }
        // 时间+1
        ++currentTime;
        // 判断所有进程是否全部运行结束
        isOver = judgeOver();
        cout << endl;
    }
}
void display() {
    cout << "各进程的周转时间和带权周转时间如下：" << endl;
    cout << "    作业名    " << "    周转时间    " << "    带权周转时间    " << endl;
    for (int i = 1; i <= SLFobject.n; ++i)
    {
        // 周转时间
        SLFobject.SLFarray[i].RunTime = SLFobject.SLFarray[i].endTime - SLFobject.SLFarray[i].arriveTime;
        // 带权周转时间
        double wightTime = 1.0 * SLFobject.SLFarray[i].RunTime / SLFobject.SLFarray[i].rserviceTime;

        cout << setw(6) << SLFobject.SLFarray[i].Name << setw(15) << SLFobject.SLFarray[i].RunTime << setw(18);
        //控制浮点数的输出精度
        cout.precision(4);
        cout << wightTime << endl;
    }
}


//3
//process1 2 10 
//process2 3 4 
//process3 3 2 



