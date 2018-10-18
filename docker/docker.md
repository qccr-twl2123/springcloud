### docker 常用命令
* docker 安装
```text
删除旧版本
yum remove -y docker docker-common docker-selinux docker-engine

安装
yum install docker 

启动
service docker start
chkconfig docker on

#LCTT 译注：此处采用了旧式的 sysv 语法，如采用CentOS 7中支持的新式 systemd 语法，如下：
systemctl  start docker.service
systemctl  enable docker.service
systemctl restart docker

中国镜像加速器
vi  /etc/docker/daemon.json
#添加后：
{
    "registry-mirrors": ["https://registry.docker-cn.com"],
    "live-restore": true
}

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

