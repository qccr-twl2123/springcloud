### docker 常用命令
* docker 安装
```text
删除旧版本
yum remove -y docker docker-common docker-selinux docker-engine

安装
yum install docker 

启动
systemctl

```
* 镜像构建常用命令
```text
mvn package docker:build

docker build --rm -t user-service .
```
* 删除全部已经停止的容器
```text
1.List all exited containers
docker ps -aq -f status=exited

2.Remove stopped containers
docker ps -aq --no-trunc -f status=exited | xargs docker rm
```

* 进入容器内部终端
```text
docker  exec -i -t  <容器ID or 容器别名>  /bin/bash
```
