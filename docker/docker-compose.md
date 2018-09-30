### docker compose 教程
* 简介
```text
1.概述
  描述服务编排的逻辑
2.将所管理的容器分为三层:工程,服务,容器
   
```
* 安装
```text
下载最新版的docker-compose文件?
$ sudo curl -L https://github.com/docker/compose/releases/download/1.16.1/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
添加可执行权限?
$ sudo chmod +x /usr/local/bin/docker-compose
测试安装结果?
$ docker-compose?--version?docker-compose?version 1.16.1, build 1719ceb
```