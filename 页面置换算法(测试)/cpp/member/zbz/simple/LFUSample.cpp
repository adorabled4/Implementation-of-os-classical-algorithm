#include<iostream>
#include<map>
#include<vector>
using namespace std;
vector<int> s;                //
map<int, int> m;
const int Max = 2;
void LFU(int n)
{
	if (m.count(n))          //判断是否已经在内存
	{
		m[n]++;				 //在 直接为此页号调度次数加一
	}
	else                     //不在
	{
		int k = m.size();	//获取内存已有页数
		if (k < Max)		//k<Max 直接放入内存，并将调用次数置一
		{
			m[n] = 1;
		}
		else                //遍历m获取最少调度的页数
		{
			int	min = 0;	//最少次数
			int	l;			//最少页数
			int x = 0;
			for (auto i : m)
			{
				if (x == 0)
				{
					min = i.second;
					l = i.first;
					x++;
					continue;
				}
				if (min > i.second)
				{
					min = i.second;
					l = i.first;
				}
			}
			m[n] = 1;		//添加新页并置一
			m.erase(l);		//删除旧页
		}
	}
}
void Print()                          //显示结果
{
	for (auto i : m)
	{
		cout << i.first << " ";
	}
	cout << endl;
}
int main()
{
	LFU(1);
	LFU(2);
	LFU(1);//输入三个值
	Print();//打印结果
	LFU(3);//调用3页
	Print();//打印结果
}