#include <iostream>
#include <queue>
#include <vector>
#include<algorithm>
using namespace std;
// 就绪态
#define W "waitting"
// 运行态
#define R "running"
// 完成态
#define F "finished"

// 进程结构体，包含进程编号，名字，到达时间，服务时间，剩余服务时间，已服务时间，完成时间点记录，周转时间，带权周转时间，状态，开始时间，优先级等信息
struct PCB {
    int id; // 进程编号
    string Name; // 进程名字
    double arriveTime; // 进程到达时间
    double serviceTime; // 进程需要服务时间
    double rserviceTime; // 进程剩余服务时间
    double endTime; // 已服务时间
    double finishTime; // 完成时间点记录
    double RunTime; // 进程周转时间
    double avgTime; // 带权周转时
    string State; // 进程状态
    int st=-1; // 开始时间
    int weight=-1; // 进程的优先级（权重）
};
class MLFQ{
public:
    int n=0; // 进程数量
    vector<int> quantums; // 每个队列的时间片
    vector<PCB> Processes; // 进程数组
    int CurrentTime=0;//记录当前时间
    void initquantums(){
        this->quantums.push_back(4);
        this->quantums.push_back(8);
        this->quantums.push_back(12);
    }

};
static MLFQ MLFQobject;
// 比较函数，按照到达时间升序排列
bool compare(PCB &p1, PCB &p2) {
    return p1.arriveTime < p2.arriveTime;
}
//初始化进程
void init(){
    //初始化队列为3个，时间片分别为4   8  12
    MLFQobject.initquantums();
    cout << "请输入队列的数量: ";
    cin >> MLFQobject.n;
    cout <<  "请输入每个进程的名字，到达时间和服务时间: ";
    for (int i = 0; i < MLFQobject.n; i++) {
        PCB p;
        p.id = i + 1;
        cin >> p.Name >> p.arriveTime >> p.serviceTime;
        p.rserviceTime = p.serviceTime;
        p.endTime = 0;
        MLFQobject.Processes.push_back(p);
    }
    // 对进程按照到达时间排序
    sort(MLFQobject.Processes.begin(), MLFQobject.Processes.end(), compare);

}
void show(){
    cout<<"当前时刻"<<"     "<<"进程名"<<"     "<<"进程剩余服务时间"<<"     "<<"进程优先级"<<"     "<<"进程状态"<<endl;
    for(int i=0;i<MLFQobject.n;i++){
        cout<<"   "<<MLFQobject.CurrentTime<<"--"<<MLFQobject.CurrentTime+1<<"           "<<MLFQobject.Processes[i].Name<<"             "<<MLFQobject.Processes[i].rserviceTime<<"                   "
            <<MLFQobject.Processes[i].weight<<"          "<<MLFQobject.Processes[i].State<<endl;
    }
    cout<<"-------------------------------------------------------------------------------"<<endl;
}
void Print(){
    //计算周转时间
    for(int i=0;i<MLFQobject.n;i++){
        MLFQobject.Processes[i].RunTime=MLFQobject.Processes[i].finishTime-MLFQobject.Processes[i].arriveTime;
        MLFQobject.Processes[i].avgTime=MLFQobject.Processes[i].RunTime/MLFQobject.Processes[i].serviceTime;
    }
    cout<<"进程名"<<"   "<<"进程结束时间"<<"   "<<"进程周转时间"<<"   "<<"带权周转时间"<<endl;
    for(int i=0;i<MLFQobject.n;i++){
        cout<<MLFQobject.Processes[i].Name<<"           "<<MLFQobject.Processes[i].finishTime<<"             "<<
            MLFQobject.Processes[i].RunTime<<"                  "<<MLFQobject.Processes[i].avgTime<<endl;
    }

}
// 多级反馈队列调度函数，参数为进程数组，队列数量，每个队列的时间片
void MLFQ() {
    // 创建3个就绪队列，每个队列用一个queue存储进程ID
    vector<queue<int>> queues(3);
    // 创建一个完成队列，用一个vector存储已完成的进程ID
    vector<int> finished;
    // 初始化当前队列的索引为0
    int current = 0;
    // 初始化当前执行的进程ID为-1，表示没有进程执行
    int running = -1;
    // 初始化当前执行的进程剩余的时间片为0
    int quantum = 0;
    // 循环直到所有进程都完成
    while (finished.size() < MLFQobject.Processes.size()) {
        // 遍历所有进程，将到达的进程加入第一个就绪队列
        for (int i = 0; i < MLFQobject.Processes.size(); i++) {
            if (MLFQobject.Processes[i].arriveTime == MLFQobject.CurrentTime) {
                queues[0].push(MLFQobject.Processes[i].id); // 将进程ID加入队列
                MLFQobject.Processes[i].weight = 0; // 设置进程的优先级为0
            }
        }
        // 如果当前没有执行的进程，从最高优先级的非空队列中选取一个进程执行

        // 如果当前有执行的进程，更新其剩余时间和时间片
        if (running != -1) {
            MLFQobject.Processes[running - 1].rserviceTime--;
            quantum--;
            MLFQobject.Processes[running - 1].endTime++;
        }
        // 如果当前执行的进程完成了，将其加入完成队列，并更新其完成时间，周转时间，带权周转时间
        if (running != -1 && MLFQobject.Processes[running - 1].rserviceTime == 0) {
            finished.push_back(running); // 将进程ID加入完成队列
            MLFQobject.Processes[running - 1].finishTime = MLFQobject.CurrentTime + 1; // 设置进程的完成时间为当前时间加1
            MLFQobject.Processes[running - 1].RunTime = MLFQobject.Processes[running - 1].finishTime - MLFQobject.Processes[running - 1].arriveTime; // 计算进程的周转时间
            MLFQobject.Processes[running - 1].avgTime = MLFQobject.Processes[running - 1].RunTime / MLFQobject.Processes[running - 1].serviceTime; // 计算进程的带权周转时间
            MLFQobject.Processes[running - 1].State = F; // 设置进程的状态为完成
            running = -1; // 将当前执行的进程ID设为-1，表示没有进程执行
        }
        // 如果当前执行的进程用完了时间片，将其降低一个优先级，放入下一个队列的末尾，除非它已经在最低优先级的队列中
        if (running != -1 && quantum == 0) {
            if (current < 3 - 1) {
                queues[current + 1].push(running); // 将进程ID加入下一个队列的末尾
                MLFQobject.Processes[running - 1].weight++; // 增加进程的优先级

                cout << "时刻  " << MLFQobject.CurrentTime + 1 << ": 进程 " << MLFQobject.Processes[running - 1].Name << " 移动到队列 " << current + 1 << endl;
            } else {
                queues[current].push(running); // 将进程ID加入当前队列的末尾
                cout << "时刻 " << MLFQobject.CurrentTime + 1 << ": 进程   " << MLFQobject.Processes[running - 1].Name << " 留在队列 " << current << endl;
            }
            MLFQobject.Processes[running - 1].State=W;
            running = -1; // 将当前执行的进程ID设为-1，表示没有进程执行
        }
        if (running == -1) {
            for (int i = 0; i < 3; i++) {
                if (!queues[i].empty()) {
                    running = queues[i].front(); // 获取队首的进程ID
                    queues[i].pop(); // 将该进程从队列中移除
                    current = i; // 更新当前队列的索引
                    quantum = MLFQobject.quantums[i]; // 更新当前执行的进程剩余的时间片
                    MLFQobject.Processes[running - 1].st = MLFQobject.CurrentTime; // 设置进程的开始时间为当前时间
                    MLFQobject.Processes[running - 1].State = R; // 设置进程的状态为运行
                    break;
                }
            }
        }
        show();
        // 增加当前时间
        MLFQobject.CurrentTime++;
    }
    // 输出所有进程的信息
    Print();
}


// 主函数，从用户输入获取参数，并调用多级反馈队列调度函数
int main() {
    system("chcp 65001");
    init();
    // 调用多级反馈队列调度函数
    MLFQ();
}
/*
A 0 10
B 1 5
C 2 3
D 3 7
E 4 12
*/