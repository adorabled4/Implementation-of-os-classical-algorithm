//
// Created by 刘晋昂 on 2023/5/9.
//
#include "../model/PCB.h"

//判断进程到达的先后顺序
bool cmpArrive(PCB &a, PCB &b)
{
    return a.arriveTime < b.arriveTime;
}
//按到达先后顺序在数组中排序
void init()
{
    cout<<"请输入进程数";
    cin>>PSobject.n;
    for(int i=0;i<PSobject.n;i++){
        cout<<"请依次输入第"<<i+1<<"个进程的姓名，进程到达时间，进程服务时间，进程的优先级";
        cin>>PSobject.PSarray[i].Name>>PSobject.PSarray[i].arriveTime>>PSobject.PSarray[i].serviceTime>>PSobject.PSarray[i].weight;
        PSobject.PSarray[i].id=i;
        //初始剩余服务时间等于服务时间
        PSobject.PSarray[i].rserviceTime=PSobject.PSarray[i].serviceTime;
    }
    sort(PSobject.PSarray,PSobject.PSarray+PSobject.n,cmpArrive);
    for(int i=0;i<PSobject.n;i++){
        cout<<PSobject.PSarray[i].id<<"  "<<PSobject.PSarray[i].Name<<endl;
    }
}
//显示每个时刻的进程情况
void show(){
    cout<<"当前时刻"<<"     "<<"进程名"<<"     "<<"进程剩余服务时间"<<"     "<<"进程优先级"<<"     "<<"进程状态"<<endl;
    for(int i=0;i<PSobject.n;i++){
        cout<<"   "<<PSobject.CurrentTime<<"--"<<PSobject.CurrentTime+1<<"           "<<PSobject.PSarray[i].Name<<"             "<<PSobject.PSarray[i].rserviceTime<<"                   "
        <<PSobject.PSarray[i].weight<<"          "<<PSobject.PSarray[i].State<<endl;
    }
    cout<<"-------------------------------------------------------------------------------"<<endl;
}
//打印最终结果，周转时间和带权周转时间
void Print(){
    //计算周转时间
    for(int i=0;i<PSobject.n;i++){
       PSobject.PSarray[i].RunTime=PSobject.PSarray[i].finishTime-PSobject.PSarray[i].arriveTime;
        PSobject.PSarray[i].avgTime=PSobject.PSarray[i].RunTime/PSobject.PSarray[i].serviceTime;
    }

    cout<<"进程名"<<"   "<<"进程结束时间"<<"   "<<"进程周转时间"<<"   "<<"带权周转时间"<<endl;
    for(int i=0;i<PSobject.n;i++){
        cout<<PSobject.PSarray[i].Name<<"           "<<PSobject.PSarray[i].finishTime<<"             "<<
        PSobject.PSarray[i].RunTime<<"                  "<<PSobject.PSarray[i].avgTime<<endl;
    }

}
//运行
void run(){
    PSobject.CurrentTime=0;//记录当前时刻
    //已完成的进程数，初始化为0
    int hFinish=PSobject.n;
    while(hFinish!=0){
//        cout<<hFinish<<endl;
        //判断当前时刻到达的进程，并将它push到容器Waitlist中
        for(int i=0;i<PSobject.n;i++){
            if( PSobject.PSarray[i].rserviceTime>0&&PSobject.PSarray[i].arriveTime<=PSobject.CurrentTime) {
                PSobject.WaitList.push_back(PSobject.PSarray[i]);
            }
        }

//        for(int i=0;i<PSobject.WaitList.size();i++){
//            cout<<PSobject.CurrentTime<<"  "<<PSobject.WaitList[i].Name<<"  "<<PSobject.WaitList[i].rserviceTime
//            <<"  "<<PSobject.WaitList[i].weight<<"   "<<PSobject.WaitList[i].State<<endl;
//        }
//        cout<<"--------------------------------------------------------------"<<endl;
        PCB tmp;
        tmp.weight=-100;
        //记录WaitList中优先级最高的下标
        int index=0;
        //执行当前等待进程中优先级最高的那一个
        for(int i=0;i<PSobject.WaitList.size();i++){
           if(PSobject.WaitList[i]. weight>tmp.weight){
               tmp=PSobject.WaitList[i];
               index=i;
           }
        }
        //优先级最高的那个进程优先级-1，剩余服务时间-1，进程状态变为running，
        for(int i=0;i<PSobject.n;i++){
            if(PSobject.PSarray[i].arriveTime<=PSobject.CurrentTime&&PSobject.PSarray[i].rserviceTime!=0){
                if(PSobject.WaitList[index].id !=PSobject.PSarray[i].id){
                    PSobject.PSarray[i].State=W;
                }
                if(PSobject.WaitList[index].id ==PSobject.PSarray[i].id){
                    PSobject.PSarray[i].State=R;
//                    cout<<"当前正在执行的进程名为"<<PSobject.PSarray[i].Name<<"    "<<PSobject.PSarray[i].State<<endl;
                    PSobject.PSarray[i].weight--;
                    PSobject.PSarray[i].rserviceTime--;
                    show();
                }
                if(PSobject.WaitList[index].id ==PSobject.PSarray[i].id&&PSobject.PSarray[i].rserviceTime==0){
                    PSobject.PSarray[i].State=F;
                    hFinish--;
                    PSobject.PSarray[i].finishTime=PSobject.CurrentTime+1;
                }
            }
        }
        //
        PSobject.CurrentTime++;
        PSobject.WaitList.clear();
    }
    cout<<"####################################################################################"<<endl;
    Print();
    cout<<"####################################################################################"<<endl;

}


