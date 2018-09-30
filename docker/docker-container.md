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
