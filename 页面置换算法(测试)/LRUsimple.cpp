#include <iostream>
#include <unordered_set>
using namespace std;
unordered_set<int> s;
void LUR(int n)
{
	if (s.size() < 4)
	{
		s.insert(n);
	}
	else
	{
		if (s.count(n) != 0)
		{
			s.erase(n);
			s.insert(n);
		}
		else
		{
			s.erase(s.begin());
			s.insert(n);
		}
	}
}
int main()
{
	LUR(1);
	LUR(2);
	LUR(3);
	LUR(4);
	LUR(6);
	LUR(7);
	LUR(4);
	LUR(6);
	LUR(8);
	LUR(2);
	LUR(1);
	for (auto i = s.begin(); i != s.end(); i++)
	{
		cout << *i << endl;
	}
}