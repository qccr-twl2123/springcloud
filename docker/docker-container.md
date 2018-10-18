### docker  container 容器
* 运行
```text
命令: docker run -参数
-d 后台运行
-p <host>:<dest> 端口映射
-name 设置容器名称
-v <hostPath>:<containerPath> 挂载路径
```
* 设置资源限制
```text
1. 内存
docker run -m 100M --memory-swap 200M <imageName>
-m 设置内存限制
--memory-swap 设置内存+swap大小
```
* cgroup(control group)
```text
cd /sys/fs/cgroup/
```

* docker inspect 查看容器基本信息(存储位置,配置参数,网络设置,运行情况)
```text
docker inspect <containerId> or <containerName>
```
* docker logs 查看容器的日志
```text
docker logs <containerId> or <containerName>
```
* docker stats 查看容器的状态(CPU,内存,网络or磁盘开销)
```text
docker stats <containerId> or <containerName>
```
* docker 容器内部执行命令
```text
1. docker exec 容器名+容器内执行的命令


2. docker exec -it <containerName> /bin/bash 
  -it 相当于以root身份登入容器内部
  exit 退出
```
* 强制删除正在运行的容器
```text
docker rm -f <containerName>
```

