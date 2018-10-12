## Nginx 集群配置
* [安装](#安装)
* [入门篇](#入门篇)
    * [配置](#配置)
    * [集群](#集群)
    * [命令](#命令)
* [API](#API) 
* [FAQ](#FAQ)
* [附录](#附录)

### 安装
* centos7 nginx 安装教程
```text
1.添加Nginx到YUM源
sudo rpm -Uvh http://nginx.org/packages/centos/7/noarch/RPMS/nginx-release-centos-7-0.el7.ngx.noarch.rpm

2.安装Nginx
sudo yum install -y nginx

3.启动Nginx
sudo systemctl start nginx.service

4.开机启动
sudo systemctl enable nginx.service
```

* conf配置
```text
location / {                       
    proxy_pass http://$group;
    proxy_set_header   Host             $host;
    proxy_set_header   X-Real-IP        $remote_addr;
    proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
    index  index.html index.htm;
  }
```


### FAQ
* nginx 安装路径?
```text
cd /etc/nginx
```
* nginx 默认日志路径
```text
cd 
```
* nginx 常见日志错误
```text
1.Permission denied) while connecting to upstream

    SeLinux是2.6版本的Linux系统内核中提供的强制访问控制（MAC）系统。算是内置的安全系统，防火墙什么的应该算是外配的。
    1.关闭SeLinux
    1.临时关闭（不用重启机器）：
    setenforce 0                  ##设置SELinux 成为permissive模式
    ##setenforce 1 设置SELinux 成为enforcing模式
    2.修改配置文件需要重启机器：
    修改/etc/selinux/config 文件
    将SELINUX=enforcing改为SELINUX=disabled
    重启机器即可
    
    2.执行下面的命令
    setsebool -P httpd_can_network_connect 1
```




