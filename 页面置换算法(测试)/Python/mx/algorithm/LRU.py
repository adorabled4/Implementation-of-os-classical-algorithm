"""LRU淘汰算法：
    1。如果此数据已经被缓存到链表中，遍历得到数据节点，将其从原位置删除，然后再插入链表头部
    2。如果数据没有再缓存链表中：缓存未满， 则直接加入头部
                            缓存已满， 则先删除尾节点，再将新的节点插入到链表

"""
data_list = []  # 定义列表
list_num = 5 # 确定缓存区大小.
# 进行算法实现
def LRUP(num):
   if num in data_list: #如果数据已经存在缓存区，把它放在链表头部
      data_list.remove(num)
      data_list.insert(0,num)
   else:
        if len(data_list) < list_num: #如果所输入数据没有被加载到缓存且缓存没有满，直接把数据添加到链表头部
            data_list.insert(0, num)
        else:
            data_list.pop(-1) #如果所输入数据没有被加载到内存且链表已满，把列表尾部删除，把新数据添加到链表头部
            data_list.insert(0, num)
   print(data_list)
LRUP(1)
LRUP(2)
LRUP(3)
LRUP(4)
LRUP(5)
LRUP(3)
LRUP(7)