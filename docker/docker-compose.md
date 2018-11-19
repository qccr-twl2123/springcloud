### docker compose 教程
* 简介
```text
Dockerfile 可以让用户管理一个单独的应用容器
Docker-Compose 则允许用户在一个模板（YAML 格式）中定义一组相关联的应用容器（被称为一个 project，即项目）
```
* 安装
```text
方案一:
1. 安装pip如果不存在
    sudo yum install python-pip 
    pip --version
2. 执行如下命令
    sudo pip install -U docker-compose
3. 添加bash补全命令(暂时不懂？)
   curl -L https://raw.githubusercontent.com/docker/compose/1.2.0/contrib/completion/bash/docker-compose > /etc/bash_completion.d/docker-compose

方案二:
sudo pip install docker-compose

验证:
docker-compose --version

运行(进入docker-compose.yml所在目录):
docker-compose up
     
```

* 启动和停止
```text
启动: docker-compose start
停止: docker-compose stop
```
[docker-compose使用](http://wiki.jikexueyuan.com/project/docker-technology-and-combat/intro.html)