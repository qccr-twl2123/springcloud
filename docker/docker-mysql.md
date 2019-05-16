### docker mysql教程

* 安装
```text
docker run -p 3306:3306 --name mymysql -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs -v $PWD/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.6
```
```text
-p 3306:3306：将容器的 3306 端口映射到主机的 3306 端口。

-v -v $PWD/conf:/etc/mysql/conf.d：将主机当前目录下的 conf/my.cnf 挂载到容器的 /etc/mysql/my.cnf。

-v $PWD/logs:/logs：将主机当前目录下的 logs 目录挂载到容器的 /logs。

-v $PWD/data:/var/lib/mysql ：将主机当前目录下的data目录挂载到容器的 /var/lib/mysql 。

-e MYSQL_ROOT_PASSWORD=123456：初始化 root 用户的密码。
```
* 客户端链接失败的bug
```text
client does not support authentication consider upgrading
```
```text
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '你的密码';
```
* mysql 设置编码utf8
```text
1.进入容器: docker exec -it <容器Id> /bin/bash 

2.进入操作目录: cd /etc/mysql

3.编辑 vim my.cnf
在[mysqld] 下面新增如下语句
character-set-server=utf8

注: 如果vim 不能使用，请按照如下步骤进行安装.

在使用docker容器时，有时候里边没有安装vim，敲vim命令时提示说：vim: command not found，这个时候就需要安装vim，可是当你敲apt-get install vim命令时，提示：
Reading package lists... Done
Building dependency tree       
Reading state information... Done
E: Unable to locate package vim
这时候需要敲：apt-get update，这个命令的作用是：同步 /etc/apt/sources.list 和 /etc/apt/sources.list.d 中列出的源的索引，这样才能获取到最新的软件包。
等更新完毕以后再敲命令：apt-get install vim命令即可。

apt-get update && apt-get install vim
apt-get update && apt-get install cron
```

* 进入mysql 控台方法
```text
mysql -u 用户名 -p之后，输入mysql密码，进入mysql控制台
```

### 容器操作
* 容器之间共享数据
```text
docker run --name mysql2 --volumes-from mysql -d mysql:5.7

mysql2 共享mysql挂载目录
```
* 备份,迁移, 恢复
```text
备份
docker run --rm --volumes-from mysql -v $(pwd):/backup mysql:5.7 tar cvf /backup/backup.tar /var/lib/mysql

恢复
docker run --rm --volumes-from mysql -v $(pwd):/backup mysql:5.7 bash -c "cd /var/lib/mysql && tar xvf /backup.tar --strip 1"

--rm 用来创建一个“用完即销”的容器，
--volumes-from 用来把一个已有容器上挂载的卷挂载到新创建的容器上
```

[mysql安装教程](https://www.linuxidc.com/Linux/2016-09/135288.htm)

[docker mysql 挂载](https://www.binss.me/blog/learn-docker-with-me-about-volume/)
