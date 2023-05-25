//
//最佳适应算法（Best - Fit）：
//  Created by dhrzbz on 2023/5/23.
// 

//最佳适应算法（Best - Fit）：
//该算法会按照⼤⼩顺序查找最⼩的能够满⾜需求的空闲分
//区，并将进程放⼊其中。

#include<iostream>
#include<vector>
#include<map>
#include <queue>
using namespace std;

bool cmp_by_value1(const pair<int, int>& lhs, const pair<int, int>& rhs) {
	return lhs.second < rhs.second;		//改变排序规则，以第二个的值为从小到大排
}
bool cmp_by_value2(const pair<int, int>& lhs, const pair<int, int>& rhs) {
	return lhs.first < rhs.first;		//改变排序规则，以第一个的值为从小到大排
}
class BF
{
private:

	int Max;
	vector<pair<int, int>> p;   //存储所剩的空间大小 key：起始地址  value：大小
public:
	BF(pair<int, int> _p)
	{
		p.push_back(_p);		//初始化空间大小
		Max = _p.second;				//记录空间最大值，防止超出
	}
	void BF_Add(int n)
	{
		sort(p.begin(), p.end(), cmp_by_value1);//按value值排序，便于找出第一个可以放下的空闲空间
		vector<pair<int, int>>::iterator it = p.begin();//使用迭代器，便于删去更新空闲空间
		for (; it != p.end(); it++)
		{
			if (n == it->second)                   //空闲空间==文件大小 直接全部赋予，删除给空闲空间
			{
				p.erase(it);
				return;
			}
			else if (n <= it->second)					//空闲空间>=文件大小 删除旧的，添加新的
			{
				int j = it->first, k = it->second;
				p.erase(it);
				p.push_back(pair<int, int>(j + n, k - n));
				return;
			}
		}
		cout << "分配失败" << endl;                //没有合适空间 
	}
	void BF_Free(int n, int length)         //释放空间
	{
		if (n + length > Max)              //超出范围
		{
			cout << "空间超出范围" << endl;
			return;
		}
		for (auto i : p)					//判断是否在可释放空间
		{
			if (i.first <= n && i.second + n >= n)
			{
				cout << "释放空间有误" << endl;
				return;
			}

		}
		p.push_back(pair<int, int>(n, length));//将新空闲位置加入
		sort(p.begin(), p.end(), cmp_by_value2);//按key值排序，便于合并相邻空闲空间
		for (int j = 0, k = 1; k < p.size(); k++)
		{
			if (p[j].first + p[j].second == p[k].first)//空间相邻，合并
			{
				vector<pair<int, int>> ::iterator it = p.begin() + k;
				p[j].second += p[k].second;
				p.erase(it);
			}
			else                                 //向后遍历
			{
				j++;
			}
		}
	}
	void Print()                         //输出剩余可用空间
	{
		for (auto i : p)
		{
			cout << i.first << " " << i.second << endl;
		}
	}
};
int main()
{
	int m, n;
	cout << "请输入初始空间大小：";
	cin >> n;
	BF ff(pair<int, int>(0, n));
	for (int i = 0; i < 5; i++)
	{
		cout << "请选择占有空间(1)或者释放空间(2)：" << endl;
		cin >> m;
		if (m == 1)
		{
			cout << "请输入文件大小：";
			cin >> n;
			cout << endl;
			ff.BF_Add(n);
		}
		else if (m == 2)
		{
			cout << "请输入释放空间的起始地址及大小" << endl;
			int x, y;
			cin >> x >> y;
			cout << endl;
			ff.BF_Free(x, y);

		}
		else
			cout << "输入错误请重新输入！" << endl;
		ff.Print();
	}

}