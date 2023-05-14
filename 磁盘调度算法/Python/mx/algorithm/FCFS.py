
def fcfs(start:int ,data:list):
    n=len(data)
    count=0
    for d in data:
        count+=abs(start-d)
        start=d
    count+=0.0
    print("FCFS:平均寻道长度为：%.1f"%(count/n))


