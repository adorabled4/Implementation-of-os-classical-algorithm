//
//⾸次适应算法（First-Fit）
//  Created by dhrzbz on 2023/5/23.
// 

//⾸次适应算法（First - Fit）：
//该算法会按照地址顺序查找第⼀个能够满⾜需求的空闲
//分区，并将进程放⼊其中。

#include<iostream>
#include<vector>
#include<map>
#include <queue>
using namespace std;

class FF
{
private:
	map<int, int> m;	//存储所剩的空间大小 key：起始地址  value：大小
	int Max;

public:
	FF(pair<int, int> p)
	{
		m[p.first] = p.second;		//初始化空间大小
		Max = p.second;				//记录空间最大值，防止超出
	}
	void FF_add(int n)
	{
		for (auto i = m.begin(); i != m.end(); i++)//遍历寻找第一个可以放下的位置
		{
			if (i->second == n)					   //若大小等于目的文件大小则将该空间完全释放掉
			{
				m.erase(i);
				cout << "放入成功" << endl;
				return;
			}
			else if (i->second > n)				  //若大小不等于目的文件大小则将剩余空间记录
			{

				int k = i->first + n;
				int j = i->second - n;
				m.erase(i);
				m[k] = j;
				cout << "放入成功" << endl;
				return;
			}
		}
		cout << "放入失败" << endl;				  //空间不够
	}
	void Free(int f, int length)
	{
		if (f + length > Max)                     //超出空间，释放失败
		{
			cout << "释放位置空间超出范围" << endl;
			return;
		}
		m[f] = length;							  //将新释放空间存入map
		map<int, int> ::iterator  j = m.begin();  //遍历开是否可以合并空闲空间	
		j++;
		for (auto i = m.begin(); j != m.end(); )
		{
			int f_1 = i->first, f_2 = i->second, n_1 = j->first, n_2 = j->second;
			if (i->first + i->second == j->first) //可以合并
			{
				j++;
				m.erase(n_1);
				m[f_1] = f_2 + n_2;
			}
			else
			{
				i++;
				j++;
			}
		}
	}
};
int main()
{
	int m, n;
	cout << "请输入初始空间大小：";
	cin >> n;
	FF ff(pair<int, int>(0, n));
	for (int i = 0; i < 5; i++)
	{
		cout << "请选择占有空间(1)或者释放空间(2)：" << endl;
		cin >> m;
		if (m == 1)
		{
			cout << "请输入文件大小：";
			cin >> n;
			cout << endl;
			ff.FF_add(n);
		}
		else if (m == 2)
		{
			cout << "请输入释放空间的起始地址及大小" << endl;
			int x, y;
			cin >> x >> y;
			cout << endl;
			ff.Free(x, y);

		}
		else
			cout << "输入错误请重新输入！" << endl;
	}

}