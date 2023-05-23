//
// Created by 刘晋昂 on 2023/5/7.
//

#ifndef IMPLEMENTATION_OF_OS_CLASSICAL_ALGORITHM_PCB_H
#define IMPLEMENTATION_OF_OS_CLASSICAL_ALGORITHM_PCB_H
#include<iostream>
#include<vector>
#include<queue>
#include<algorithm>
#include <string>
#include <iomanip>
using namespace std;
// 就绪态
#define W "waitting"
// 运行态
#define R "running"
// 完成态w
#define F "finished"
//最多100个进程
const int N = 100;
//通用属性
struct PCB {
    int id;	//进程编号
    string Name;//进程名字
    double arriveTime;//进程到达时间
    double serviceTime;//进程需要服务时间
    double rserviceTime;//进程剩余服务时间
    double endTime;//已服务时间
    double finishTime;//完成时间点记录
    double RunTime;//进程周转时间
    double avgTime;	//带权周转时
    string State;//进程状态
    int st; // 开始时间
    int weight;//进程的优先级（权重）
};

//声明一个RR类
class RR{
public:
    queue<PCB>RRqueue;  //用来模拟进程执行RR调度算法的队列
    double SumWT=0,SumWWT=0,AverageWT =0,AverageWWT=0;//平均周转时间、平均带权周转时间
    int q;  //时间片数
    int n;  //进程个数
    PCB RRarray[N];  //进程结构体

};
static RR RRobject;

//声明一个SLF类
class SLF{
public:
    double SumWT=0,SumWWT=0,AverageWT =0,AverageWWT=0;//平均周转时间、平均带权周转时间
    int q;  //时间片数
    int n;  //进程个数
    PCB  SLFarray[N];  //进程结构体

};
static SLF SLFobject;

//声明一个PS类
class PS{
public:
    PCB PSarray[N];
    int n;
    double CurrentTime;//声明当前时间
    vector<PCB> WaitList;
};
static  PS PSobject;

//声明一个SRTF类
class SRTF{
public:
    int n;//进程数
    vector<PCB> SRTFarray;
};
static SRTF SRTFobject;
#endif //IMPLEMENTATION_OF_OS_CLASSICAL_ALGORITHM_PCB_H
