//
// Created by 刘晋昂 on 2023/5/14.
//
#include "../model/PCB.h"

// 定义一个比较函数，按照到达时间升序排序
bool cmpArrive(const PCB& p1, const PCB& p2) {
    return p1.arriveTime < p2.arriveTime;
}

void init()
{
    cout<<"请输入进程数";
    cin>>SRTFobject.n;
    for(int i=0;i<SRTFobject.n;i++){
        PCB tmp;
        tmp.id=i;
        cout<<"请依次输入第"<<i+1<<"个进程的姓名，进程到达时间，进程服务时间";
        cin>>tmp.Name>>tmp.arriveTime>>tmp.serviceTime;
        //初始剩余服务时间等于服务时间
        tmp.rserviceTime=tmp.serviceTime;
        SRTFobject.SRTFarray.push_back(tmp);
    }
    
    sort(SRTFobject.SRTFarray.begin(), SRTFobject.SRTFarray.end(), cmpArrive);
}
// 定义一个函数，实现SrserviceTimeF调度算法
void Run() {
    // 定义一个变量表示当前时间
    int CurrentTime = 0;
    // 定义一个变量表示已完成的进程数
    int hFinish = 0;
    // 定义一个变量表示当前执行的进程编号，初始为-1表示没有进程执行
    int currentPCB = -1;
    // 循环直到所有进程都完成
    while (hFinish < SRTFobject.SRTFarray.size()) {
        // 遍历所有进程，找出当前时间可以执行的剩余时间最短的进程
        int shortestPCB = -1;
        int shortestTime  = INT_MAX;
        for (int i = 0; i < SRTFobject.SRTFarray.size(); i++) {
            if (SRTFobject.SRTFarray[i].arriveTime <= CurrentTime && SRTFobject.SRTFarray[i].rserviceTime > 0) {
                SRTFobject.SRTFarray[i].State=W;
                if (SRTFobject.SRTFarray[i].rserviceTime < shortestTime ) {
                    shortestPCB = i;
                    shortestTime  = SRTFobject.SRTFarray[i].rserviceTime;
                }
            }
        }

        // 如果找到了可执行的进程，更新当前执行的进程编号
        if (shortestPCB != -1) {
            if( SRTFobject.SRTFarray[currentPCB].rserviceTime==0){
                SRTFobject.SRTFarray[currentPCB].State=F;
            }
           else  SRTFobject.SRTFarray[currentPCB].State=W;
            currentPCB = shortestPCB;
            SRTFobject.SRTFarray[currentPCB].State=R;
        }
        // 如果没有找到可执行的进程，说明当前没有进程到达，将当前时间加一后继续循环
        if (currentPCB == -1) {
            SRTFobject.SRTFarray[currentPCB].State=R;
            CurrentTime++;
            continue;
        }
        // 如果当前执行的进程是第一次执行，记录其开始时间
        if (SRTFobject.SRTFarray[currentPCB].st == -1) {
            SRTFobject.SRTFarray[currentPCB].st = CurrentTime;
            SRTFobject.SRTFarray[currentPCB].State=R;
        }

        // 将当前执行的进程的剩余时间减一，表示执行了一个单位时间
        SRTFobject.SRTFarray[currentPCB].rserviceTime--;

        // 如果当前执行的进程的剩余时间为零，表示该进程已完成，记录其完成时间、等待时间和周转时间，并将已完成的进程数加一
        if (SRTFobject.SRTFarray[currentPCB].rserviceTime == 0) {
            SRTFobject.SRTFarray[currentPCB].finishTime = CurrentTime + 1;
            SRTFobject.SRTFarray[currentPCB].State=F;
            //计算周转时间和带权周转时间
            SRTFobject.SRTFarray[currentPCB].RunTime = SRTFobject.SRTFarray[currentPCB].finishTime - SRTFobject.SRTFarray[currentPCB].arriveTime;
            SRTFobject.SRTFarray[currentPCB].avgTime = SRTFobject.SRTFarray[currentPCB].RunTime / SRTFobject.SRTFarray[currentPCB].serviceTime;
            hFinish++;
        }
        // 将当前时间加一，表示进入下一个单位时间
            cout<<"当前时刻"<<"     "<<"进程名"<<"     "<<"进程剩余服务时间"<<"     "<<"进程状态"<<endl;
            for(int i=0;i<SRTFobject.n;i++){
                cout<<"   "<<CurrentTime<<"--"<<CurrentTime+1<<"           "<<SRTFobject.SRTFarray[i].Name<<"             "<<SRTFobject.SRTFarray[i].rserviceTime<<"                   "
                    <<"          "<<SRTFobject.SRTFarray[i].State<<endl;
            }
            cout<<"-------------------------------------------------------------------------------"<<endl;
        CurrentTime++;
    }

}
//打印周转时间和带权周转时间
void Print(){
    cout<<"进程名"<<"   "<<"进程结束时间"<<"   "<<"进程周转时间"<<"   "<<"带权周转时间"<<endl;
    for(int i=0;i<SRTFobject.n;i++){
        cout<<SRTFobject.SRTFarray[i].Name<<"           "<<SRTFobject.SRTFarray[i].finishTime<<"             "<<
            SRTFobject.SRTFarray[i].RunTime<<"                  "<<SRTFobject.SRTFarray[i].avgTime<<endl;
    }

}

