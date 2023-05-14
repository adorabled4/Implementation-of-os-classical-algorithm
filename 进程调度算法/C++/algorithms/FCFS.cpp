//
// Created by 刘晋昂 on 2023/5/7.
//

#include "../model/PCB.h"
//按到达时间排序
bool myCompare_ArriveTime(PCB &P1, PCB &P2) {

    return P1.arriveTime < P2.arriveTime;
}
//显示当前进程状态情况
void showData_Vector(vector<PCB>& v) {
    cout << "-------------------------------------------------当前进程情况-------------------------------------------------" << endl;
    cout << "进程名\t" << "进程到达时间\t" << " 进程服务时间\t" << "进程已服务时间\t" << " 进程完成时间\t" << " 进程周转时间\t" << "进程当前状态\t" << endl;
    for_each(v.begin(), v.end(), [](PCB p) {
        cout << p.Name << "\t    " <<
             p.arriveTime<< "\t\t\t" <<
             p.serviceTime << "\t\t" <<
             p.endTime << "\t\t" <<
             p.finishTime << "\t\t" <<
             p.RunTime << "      \t"
             << p.State << endl;
    });
    cout << "--------------------------------------------------------------------------------------------------------------" << endl;
}

void showData(queue<PCB>& q) {
    cout << "-------------------------------------------------当前进程情况-------------------------------------------------" << endl;
    cout << "进程名\t" << "进程到达时间\t" << " 进程服务时间\t" << "进程已服务时间\t" << " 进程完成时间\t" << " 进程周转时间\t" << "进程当前状态\t" << endl;
    while (q.size() != 0) {
        cout << q.front().Name <<"\t    " << q.front().arriveTime <<  "\t\t\t"
             << q.front().serviceTime << "\t\t"  << q.front().endTime <<"\t\t"
             << q.front().finishTime << "\t\t" << q.front().RunTime << "      \t" <<q.front().State << endl;
        q.pop();
    }

}
/*
	先来先服务算法
*/
//初始化
void init(vector<PCB> &v){
    cout << "请输入进程个数：";
    int sum;
    int increatment = 0;
    cin >> sum;
    increatment = sum - 1;
    PCB p;

    for (int i = 1; i <= sum; i++) {
        cout << "请输入第 " << i << " 个进程名字,到达时间,需要服务的时间";
        cin >> p.Name >> p.arriveTime>> p.serviceTime;
        p.State = "就绪状态";
        p.endTime = 0;
        p.finishTime = 0;
        p.RunTime = 0;
        //把进程放入容器中
        v.push_back(p);
    }
}

void FCFS() {
    vector<PCB>v;
    queue<PCB>readyList;
    init(v);
    //按进程到达的时间排序
    sort(v.begin(), v.end(), myCompare_ArriveTime);
    //展示当前情况
    showData_Vector(v);
    //把数据放入队列中
    for (vector<PCB>::iterator it = v.begin(); it != v.end(); it++) {
        readyList.push(*it);
    }
    int tempTime = 0;
    queue<PCB>endlist;
    PCB temp;
    //获取第一个数据前的操作,打印当前时间tempTime时执行的进程
    while (1) {
        if (tempTime == v.front().arriveTime) {
            cout << "-------------------------------------------------------" << endl;
            cout << "第" << tempTime << "秒" << readyList.front().Name << "进程到达" << endl;
            cout << "-------------------------------------------------------" << endl;
            break;
        }
        else {
            cout << "第" << tempTime << "秒没有进程被执行" << endl;
            //执行时间片++
            tempTime++;
        }
    }

    //拿到第一个开始执行下一步操作：
    while (readyList.size() != 0)//就绪队列不为空执行
    {
        if (tempTime < readyList.front().arriveTime) {
            tempTime++;
            if (tempTime == readyList.front().arriveTime) {
                cout << "-------------------------------------------------------" << endl;
                cout << "第" << tempTime << "秒" << readyList.front().Name << "进程到达" << endl;
                cout << "-------------------------------------------------------" << endl;
            }
        }

        if (readyList.front().serviceTime != 0) {
            //如果时间还需服务时间不为0则做相关的加或者减
            cout << "第" << tempTime << "秒 -" << readyList.front().Name << "- 正在执行" << endl;
            tempTime++;
            readyList.front().endTime++;
            readyList.front().serviceTime--;
        }

        if (readyList.front().serviceTime == 0) {
            //如果第一个数据的所需服务时间为0了那么这个数据所需执行的时间够了则交给下一个程序
            cout << "-------------------------------------------------------" << endl;
            cout << "第" << tempTime << "秒 -" << readyList.front().Name << "- 已执行完毕" << endl;
            cout << "-------------------------------------------------------" << endl;
            //把结束时间标记上
            readyList.front().finishTime = tempTime;
            //把状态进行修改
            readyList.front().State = "执行完毕";
            //计算周转时间
            readyList.front().RunTime = tempTime - readyList.front().arriveTime;
            //放入另一个队列中保存起来
            endlist.push(readyList.front());
            //弹出第一个数据让下一个开始执行
            readyList.pop();
            //满足这个条件的话就输出进程到达的时间
            if (readyList.size() != 0) {
                if (tempTime >= readyList.front().arriveTime) {
                    cout << "-------------------------------------------------------" << endl;
                    cout << "第" << readyList.front().arriveTime << "秒" << readyList.front().Name << "进程到达" << endl;
                    cout << "-------------------------------------------------------" << endl;
                }
            }
        }
    }
    queue<PCB>result;
    result = endlist;
    showData(endlist);
    //平均周转时间
    double avgTime = 0;
    double sumTime = 0;
    int size = result.size();
    while (result.size() != 0) {
        sumTime += result.front().RunTime;
        result.pop();
    }
    avgTime = sumTime / size;
    cout << "平均周转时间：" << avgTime << endl;
    cout << "--------------------------------------------------------------------------------------------------------------" << endl;
}



