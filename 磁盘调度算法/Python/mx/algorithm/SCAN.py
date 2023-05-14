#先向大方向运动
def scan(start:int,data:list):
    n=len(data)
    small=[]
    big=[]
    for d in data:
        if d<=start:
            small.append(d)
        else:
            big.append(d)
    small.sort(reverse=True)
    big.sort(reverse=False)
    count=0
    data1=big+small
    for d in data1:
        count+=abs(start-d)
        start=d
    count += 0.0
    print("FCFS:平均寻道长度为：%.1f" % (count / n))
