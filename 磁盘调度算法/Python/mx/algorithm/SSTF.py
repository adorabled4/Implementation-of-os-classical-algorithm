def findnext(start:int,data:list):
    count=abs(data[0]-start)
    flag=data[0]
    for d in data:
        l=abs(start-d)
        if l<count:
            flag=d
            count=l
    return flag
def sstf(start:int,data:list):
    count=0
    n=len(data)
    for i in range(n) :
        flag=findnext(start,data)
        count+=abs(start-flag)
        start=flag
        data.remove(flag)
    print("SSTF:平均寻道长度为：%.1f" % (count / n))
