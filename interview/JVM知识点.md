* JVM堆内存相关的启动参数：年轻代、老年代和永久代的内存分配
* jmap -heap pid就可以查看内存分配和使用情况
![输入图片说明](https://github.com/qccr-twl2123/springcloud/blob/master/images/jvm内存分配.png "在这里输入图片标题")

```text
-Xms 和 -Xmx (-XX:InitialHeapSize 和 -XX:MaxHeapSize)：指定JVM初始占用的堆内存和最大堆内存
-XX:NewSize 和 -Xmn(-XX:MaxNewSize)：指定JVM启动时分配的新生代内存和新生代最大内存
-XX:SurvivorRatio：设置新生代中1个Eden区与1个Survivor区的大小比值。
eg:在hotspot虚拟机中，新生代 = 1个Eden + 2个Survivor。
   如果新生代内存是10M，SurvivorRatio=8，那么Eden区占8M，2个Survivor区各占1M。
```

* 什么情况会发生栈内存溢出?
```text
1.是否有递归调用
2.是否有大量循环或死循环
3.全局变量是否过多
4.数组、List、map数据是否过大
```

* 线上内存溢出排查
````text
jps 进程总览

jinfo  进程信息
jstack  jstack能得到运行java程序的java stack和native stack的信息。可以轻松得知当前线程的运行情况
jstate 对内存的情况，及垃圾回收的次数  jstate -gc pid
jmap 导出dump 堆内存的快照

jvisula 查看堆快照的显示情况
````