#include<iostream>
#include<map>
#include<vector>

using namespace std;
vector<int> s;                //
map<int, int> m;
const int Max = 2;
void LFU(int n)
{
	if (m.count(n))          //�ж��Ƿ��Ѿ����ڴ�
	{
		m[n]++;				 //�� ֱ��Ϊ��ҳ�ŵ��ȴ�����һ
	}
	else                     //����
	{
		int k = m.size();	//��ȡ�ڴ�����ҳ��
		if (k < Max)		//k<Max ֱ�ӷ����ڴ棬�������ô�����һ
		{
			m[n] = 1;
		}
		else                //����m��ȡ���ٵ��ȵ�ҳ��
		{
			int	min = 0;	//���ٴ���
			int	l;			//����ҳ��
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
			m[n] = 1;		//�����ҳ����һ
			m.erase(l);		//ɾ����ҳ
		}
	}
}