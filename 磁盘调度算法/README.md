## 磁盘调度算法
磁盘调度算法的目的很简单，就是为了提高磁盘的访问性能，一般是通过优化磁盘的访问请求顺序来做到的。
寻道的时间是磁盘访问最耗时的部分，如果请求顺序优化的得当，必然可以节省一些不必要的寻道时间，从而提高磁盘的访问性能。
###  FCFS调度(先来先服务)
磁盘调度的最简单形式当然是先来先服务（FCFS）算法。虽然这种算法比较公平，但是它通常并不提供最快的服务。
例如，考虑一个磁盘队列，其 I/O 请求块的柱面的顺序如下： 98,183,37,122,14,124,65,67。
如果磁头开始位于柱面 53，那么它首先从 53 移到 98，接着再到 183、37、122、14、124、65，最后到 67，磁头移动柱面的总数为 640。
![](http://oss.dhx.icu/dhx/1.png)

### SSTF调度(最短寻道时间优先)
在移动磁头到别处以便处理其他请求之前，处理靠近当前磁头位置的所有请求可能较为合理。这个假设是最短寻道时间优先（SSTF）算法的基础。SSTF 算法选择处理距离当前磁头位置的最短寻道时间的请求。换句话说，SSTF 选择最接近磁头位置的待处理请求。
对于上面请求队列的示例，与开始磁头位置（53）的最近请求位于柱面 65。一旦位于柱面 65，下个最近请求位于柱面 67。从那里，由于柱面 37 比 98 还要近，所以下次处理 37。如此，会处理位于柱面 14 的请求，接着 98，122，124，最后183。
![](http://oss.dhx.icu/dhx/2.png)

### SCAN调度(电梯算法)
对于扫描算法，磁臂从磁盘的一端开始，向另一端移动；在移过每个柱面时，处理请求。当到达磁盘的另一端时，磁头移动方向反转，并继续处理。磁头连续来回扫描磁盘。SCAN 算法有时称为电梯算法，因为磁头的行为就像大楼里面的电梯，先处理所有向上的请求，然后再处理相反方向的请求。
下面回到前面的例子来说明。在采用 SCAN 来调度柱面 98、183、37、122、14、124、65 和 67 的请求之前，除了磁头的当前位置，还需知道磁头的移动方向。
假设磁头朝 0 移动并且磁头初始位置还是 53，磁头接下来处理 37，然后 14。在柱面 0 时，磁头会反转，移向磁盘的另一端，并处理柱面 65、67、98、122、124、183（图 3）上的请求。如果请求刚好在磁头前方加入队列，则它几乎马上就会得到服务；如果请求刚好在磁头后方加入队列，则它必须等待，直到磁头移到磁盘的另一端，反转方向，并返回。
![](http://oss.dhx.icu/dhx/3.png)

### C-SCAN
首先自里向外访问，当磁头移到最外的磁道并访问后，磁头返回到最里的欲访问磁道，即将最小磁道号紧接着最大磁道号构成循环，继续循环扫描 直至无更外的磁道需要访问时，才将磁臂换向为自外向里移动；
下一个访问的磁道在当前位置内为距离最近者；直至再无更里面的磁道要访问。
![](http://oss.dhx.icu/dhx/5.png)