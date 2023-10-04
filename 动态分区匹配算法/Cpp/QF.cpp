//
//快速适应算法（Quick-Fit）
//  Created by dhrzbz on 2023/5/24
// 

//快速适应算法（Quick - Fit）：
//该算法是⼀个改进的最佳适应算法，它会为不同⼤⼩的
//分区维护⼀个独⽴的链表，以便更快地查找适合的空闲分区。

#include<iostream>
#include<vector>
using namespace std;


class QF
{
private:
	int Max;							//最多级数
	vector<int>    q;					//存储当前位置的可存空间大小
	vector<vector<int>> p;				//存储可用空间的首地址
public:
	QF(int n)							//初始化
	{
		int i = 1;
		Max = n;
		for (int j = 0; j < Max - 1; j++)	//预留一个位置
		{
			if (j == 0)					//第一个两个空间方便合成大空间
			{
				i = i << 10;			//最小1kB
				q.push_back(i);			//可存空间大小
				vector<int> v;
				v.push_back(0);
				v.push_back(i);
				p.push_back(v);			//存储可用空间的首地址
			}
			else
			{
				i = i << 1;
				q.push_back(i);			//可存空间大小
				vector<int> v;
				v.push_back(i);
				p.push_back(v);			//存储可用空间的首地址
			}
		}
		q.push_back(i << 1);			//最后一个用来存储全部合成的大空间
		vector<int> v;
		p.push_back(v);
	}
	bool Add(int n)						//遍历来寻找合适空间
	{
		for (int i = 0; i < Max; i++)	//找到合适空间，直接分配
		{
			if (q[i] >= n && q[i] / 2 < n)
			{
				if (p[i].empty() != true)
				{
					vector<int> ::iterator it = p[i].begin();
					p[i].erase(it);
					return true;
				}
			}
			else if (q[i] / 2 >= n && i > 0)//找不到合适空间，拆分更高一级的为较小空间存储
			{
				vector<int> ::iterator it = p[i].begin();
				p[i].erase(it);
				p[i - 1].push_back(q[i] + q[i - 1]);
				return true;
			}
		}
		return false;
	}
	void QF_Add(int n)						//分配空间
	{
		if (Add(n))							//第一次尝试
			return;
		for (int i = 0; i < Max; i++)		//合并空间再次尝试
		{
			if (p[i].size() >= 2)
			{
				vector<int> ::iterator it = p[i].end() - 1;
				vector<int> ::iterator it1 = it - 1;
				p[i].erase(it);
				int k = *it1;
				p[i].erase(it1);
				vector<int> ::iterator it2 = p[i + 1].begin();
				p[i + 1].insert(it2, k);
			}
			if (Add(n))							//再次尝试
				return;
		}
		cout << "空间不够，放入失败" << endl;//合并后仍不够，放入失败
		return;
	}
	void QF_Free(int n, int length)			//释放空间 n 起始位置
	{
		if (!(n == 0 || n % 1024 == 0))		//判断起始位置是否正确
		{
			cout << "输入有误" << endl;
			return;
		}
		for (int i = 0; i < Max; i++)		//找到合适位置释放，并放入该数组合适位置（按从小到大排）
		{
			if (q[i] >= length)
			{
				for (vector<int> ::iterator it = p[i].begin(); it != p[i].end(); it++)
				{
					if (*it > n)			//在中间或者开头
					{
						p[i].insert(it, n);
						return;
					}
				}
				p[i].push_back(n);			//n为最大
				return;
			}
		}
		return;
	}
	void Print()                         //输出剩余可用空间
	{
		int k = 0;
		for (vector<vector<int>> ::iterator i = p.begin(); i != p.end(); i++, k++)
		{

			if (i->empty())				//该大小空间无可用，不输出
				continue;
			cout << q[k] << " ";
			for (vector<int> ::iterator j = i->begin(); j != i->end(); j++)
			{
				cout << *j << " ";
			}
			cout << endl;
		}
	}
};
int main()
{

	QF qf = QF(5);						//初始化为5级
	qf.Print();							//输出可用空间
	cout << endl;
	qf.QF_Add(1000);					//占用1000大小空间
	qf.Print();							//输出可用空间
	cout << endl;
	qf.QF_Free(0, 1000);					//释放0，1000位置大小空间
	qf.Print();							//输出可用空间
	cout << endl;
	qf.QF_Add(16000);					//占用16000大小空间
	qf.Print();							//输出可用空间
}