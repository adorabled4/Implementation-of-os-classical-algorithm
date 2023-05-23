//
//最不常?（LIFO）调度算法
//  Created by 朱宾振 on 2023/5/23.
// 
// 
//最不常?（LIFO）调度算法：优先处理最近不常?的磁盘请求。
//这种调度算法可以减少
//磁盘访问时间，但可能会导致?些常?的磁盘请求?时间等待。
//

#include <algorithm>
#include<iostream>
#include<map>
#include<vector>

using namespace std;
bool cmp_by_value(const pair<int, int>& lhs, const pair<int, int>& rhs) {
	return lhs.second < rhs.second;					//改变排序规则，以map的值为从小到大排
}
class LFIO
{
private:
	int Max;						//磁盘请求数目			
	vector<pair<int, int>> v;		//磁盘请求序列及最近访问次数
public:

	LFIO(map<int, int> m, int n)	//初始化map存入vector数组
	{
		Max = n;
		for (auto i : m)
		{
			v.push_back(i);
		}
	}
	void handle()
	{
		sort(v.begin(), v.end(), cmp_by_value);//给vector排序
		for (auto i : v)//按排好顺序处理请求序列
		{
			cout << i.first << endl;
		}
		cout << "处理完成" << endl;
		v.clear();//清空数组序列

	}
};
int main()
{
	cout << "请输入磁盘请求数目：";
	int n; // 磁盘请求数目
	cin >> n;
	map<int, int> m; // 磁盘请求序列
	int x, y;
	cout << "请输入磁盘请求序列：" << endl;
	for (int i = 0; i < n; i++) {
		cin >> x >> y; // 输入磁盘请求目标，次数
		m.insert(pair<int, int>(x, y));
	}
	LFIO lifo(m, n);//创建LIFO序列
	lifo.handle();//调用handel处理
	return 0;
}