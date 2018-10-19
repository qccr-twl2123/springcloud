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

* 容器共享数据
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

--rm 
```
[docker mysql 挂载](https://www.binss.me/blog/learn-docker-with-me-about-volume/)