//
// Created by 刘晋昂 on 2023/5/14.
//

#include "../algorithms/SRTF.cpp"

// 主函数，测试SrserviceTimeF调度算法
int main() {
    system("chcp 65001");
    init();
    // 调用SrserviceTimeF调度算法函数
    Run();
    Print();

    // 返回0表示程序正常结束
    return 0;
}