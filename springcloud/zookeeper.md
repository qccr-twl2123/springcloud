### zookeeper 教程
* 五大特性
```text
严格一致性：从同一客户端发起的请求，最终将会严格地按照其发起顺序被 ZooKeeper 集群处理；

原子性：来自于分布式系统的每一个请求，集群中所有服务节点要么全部对其进行了成功处理，要么都未对其进行处理。不会出现部分节点成功处理，其余节点未成功处理的情况；

单一视图：客户端连接任何一个 ZooKeeper 服务器节点，其看到的数据总是一致的；

可靠性：当某个请求被服务器节点成功处理后，该请求所引起的服务端状态变更将会被存储下来，直到有另外一个请求又对其进行了变更；

实时性：一般情况下，由客户端发起的请求被成功处理后，其他客户端能够立即获取服务端最新数据状态。
```



[zookper 下载地址](http://mirror.bit.edu.cn/apache/zookeeper/)

[zookeeper 安装教程](https://www.cnblogs.com/happyflyingpig/p/8436973.html)

[zookeeper 教程-IBM](https://www.ibm.com/developerworks/cn/opensource/os-cn-zookeeper/index.html)