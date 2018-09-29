### dockerfile

* 介绍
```text
1. 概述
一个描述镜像构建的文本文件.

2. 命令格式
docker build -t <镜像名> .

3. 常见指令
    ADD 用于复制文件
    --ADD <src>...<dest> 
    
    CMD: 容器启动命令
    
    COPY: 与ADD类似,只是不支持url或压缩包
   
    ENTRYPOINT: 与CMD类似
    
    EXPOSE: 指定暴露端口
    
    FROM: 指定基础镜像
    
    RUN: 在shell 终端运行,RUN command,RUN["executable","param1","param2"]
    eg: RUN ["/bin/bash","-c","echo start build"]
    
    VOLUME: 指定挂载点,该指令使容器中一个目录拥有持久化存储功能
    VOLUME /data
    
    WORKDIR: 指定工作目录,类似cd. 在该指令之后的RUN,CMD,ENTRYPOINT都将该目录作为当前目录
```

### 实战案例
* wordpress搭建
```text
docker pull mysql
docker run --name some-wordpress --link some-mysql:mysql -d wordpress
docker run --name mysql_wordpress -e MYSQL_ROOT_PASSWORD=wordpress  -d  mysql
docker run --name docker_wordpress --link mysql_wordpress:mysql -p 8080:80 -d wordpress

```
