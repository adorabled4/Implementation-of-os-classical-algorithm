//
// Created by 刘晋昂 on 2023/5/8.
//

#include "../model/PCB.h"

//输入时间片、到达时间、服务时间等
void init(){

    cout<<"请输入进程个数和时间片q的大小 "<<endl;
    cin>>RRobject.n>>RRobject.q;
    cout<<"请输入进程名字，到达时间,服务时间 "<<endl;
    for (int i=0;i<RRobject.n;i++){
        cin>>RRobject.RRarray[i].Name>>RRobject.RRarray[i].arriveTime>>RRobject.RRarray[i].serviceTime;
    }
    //根据达时间排序,若到达时间相同，短作业优先
    for(int i=0;i<RRobject.n;i++) {
        for(int j=i+1;j<RRobject.n;j++) {
            if(RRobject.RRarray[i].arriveTime > RRobject.RRarray[j].arriveTime) {
                PCB temp;
                temp = RRobject.RRarray[i];
                RRobject.RRarray[i] = RRobject.RRarray[j];
                RRobject.RRarray[j] = temp;
            }
            if(RRobject.RRarray[i].arriveTime == RRobject.RRarray[j].arriveTime){
                if(RRobject.RRarray[i].serviceTime > RRobject.RRarray[j].serviceTime){
                    PCB temp;
                    temp = RRobject.RRarray[i];
                    RRobject.RRarray[i] = RRobject.RRarray[j];
                    RRobject.RRarray[j] = temp;
                }
            }
        }
    }
    //测试数组是否排序
//    for(int i=0;i<RRobject.n;i++){
//        cout<<RRobject.RRarray[i].Name<<endl;
//    }

}

//A 3 2
//B 2 3
//C 2 4
//执行RR调度算法
void RRArithmetic(){
    RRobject.RRqueue.push(RRobject.RRarray[0]);   //第一个进程进队列
    int CurrentTime=0;//当前时间
    int tempTime;   //控制CurrentTime的累加时间，当前进程的服务时间小于时间片q的时候，起到重要作用
    int i=1;
    int processNumber = 0;   //执行RR算法后，进程的个数
    string processName[100];   //存储每个时间片p对应的进程名称
    int Time[100];
    //判断第一个进程的服务时间是否大于时间片，如果大于CurrentTime=q，如果小于CurrentTime=服务时间
    //一般来说CurrentTime=第一个进程的到达时间
    if (RRobject.RRarray[0].serviceTime<=RRobject.q)
        CurrentTime = RRobject.q;
    else
        CurrentTime = RRobject.RRarray[0].serviceTime;
    while(!RRobject.RRqueue.empty()){
        for (int j=i;j<RRobject.n;j++){   //使得满足进程的到达时间小于当前时间的进程都进入队列
            if (RRobject.RRarray[j].Name.length()!=0&& CurrentTime >= RRobject.RRarray[j].arriveTime){
                RRobject.RRqueue.push(RRobject.RRarray[j]);
                i++;
            }
        }
//        for(int k = 0; k <  RRobject.RRqueue.size(); k++) {
//            cout<<RRobject.RRqueue.front().Name<<"  " ;
//            RRobject.RRqueue.push( RRobject.RRqueue.front());
//            RRobject.RRqueue.pop();
//        }
     //   cout<<endl;
        //队列首进程进行执行，进程每执行一次，就将其服务时间 -q，一般来说temptime等于时间片
        if (RRobject.RRqueue.front().serviceTime>=RRobject.q)
            tempTime = RRobject.q;
        else
            tempTime = RRobject.RRqueue.front().serviceTime;


        RRobject.RRqueue.front().serviceTime -= RRobject.q;
//        cout<<"输出当前进程剩余服务时间";
//        cout<<RRobject.RRqueue.front().Name<<RRobject.RRqueue.front().serviceTime<<endl;
        //将队首进程的名称放入数组中
        processName[processNumber]= RRobject.RRqueue.front().Name;
        Time[processNumber] = CurrentTime;
        processNumber++;
        if (RRobject.RRqueue.front().serviceTime <= 0)  //把执行完的进程退出队列3 #3
            RRobject.RRqueue.pop();   //如果进程的服务时间小于等于，即该进程已经服务完了，将其退栈
        else{
            //将队首移到队尾
            RRobject.RRqueue.push(RRobject.RRqueue.front());
            RRobject.RRqueue.pop();
        }
        CurrentTime += tempTime;
    }
    //测试
    //进程输出处理   每个时间段对应的执行的进程
    int time = Time[0];
    int count = 0;
    //计算完成时间
    for (int i=0;i<processNumber;i++)
    {
        count = 0;
        while(RRobject.RRarray[count].Name!=processName[i] &&count<RRobject.n)//计算出processNumber[i]是哪个进程下标count
        {
            count++ ;
        }
        RRobject.RRarray[count].finishTime=time ;
        if (i<processNumber-1)
        {
            time = Time[i+1];
        }
    }

    //周转时间、带权周转时间、平均周转时间、带权平均周转时间的计算
    for (int i=0;i<RRobject.n;i++){
        RRobject.RRarray[i].RunTime = RRobject.RRarray[i].finishTime - RRobject.RRarray[i].arriveTime;
        RRobject.RRarray[i].avgTime = (double)RRobject.RRarray[i].RunTime/RRobject.RRarray[i].serviceTime;
    }

    for (int i=0;i<RRobject.n;i++){
        RRobject.SumWT += RRobject.RRarray[i].RunTime;
        RRobject.SumWWT += RRobject.RRarray[i].avgTime;
    }
    RRobject.AverageWT = RRobject.SumWT/RRobject.n;
    RRobject.AverageWWT = RRobject.SumWWT/RRobject.n;

        for(int j=0;j<processNumber;j++){
            cout<<Time[j]-RRobject.q<<"--"<<Time[j]<<"时刻"<<setw(6)<<"正在执行进程"<<processName[j];
            for(int k=0;k<RRobject.n;k++){
                if(RRobject.RRarray[k].arriveTime==Time[j]){
                    cout<<"   进程"<<RRobject.RRarray[k].Name<<"到达";
                }
                else if(RRobject.RRarray[k].finishTime==Time[j]&&Time[j]!=0){
                    cout<<"   进程"<<RRobject.RRarray[k].Name<<"完成";
                }
            }
            cout<<endl;

        }
}

//显示各时间执行情况，以及各个时间值
void output(){
    int i;
    //输出各个时间
    cout<<"ID"<<"\t";
    cout<<"到达时间"<<"\t";
    cout<<"服务时间"<<"\t";
    cout<<"完成时间"<<"\t";
    cout<<"周转时间"<<"\t";
    cout<<"带权周转时间"<<endl;
    for (i=0;i<RRobject.n;i++){
        cout<<RRobject.RRarray[i].Name<<"\t\t";
        cout<<RRobject.RRarray[i].arriveTime<<"\t\t";
        cout<<RRobject.RRarray[i].serviceTime<<"\t\t";
        cout<<RRobject.RRarray[i].finishTime<<"\t\t";
        cout<<RRobject.RRarray[i].RunTime<<"\t\t";
        cout<<setprecision(3)<<RRobject.RRarray[i].avgTime<<"\t\t"<<endl;;
    }
    cout<<"平均周转时间 = "<<setprecision(3)<<RRobject.AverageWT<<endl;
    cout<<"平均带权周转时间 = "<<setprecision(3)<<RRobject.AverageWWT<<endl;
}


