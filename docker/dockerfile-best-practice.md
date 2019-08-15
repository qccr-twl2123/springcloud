### docker 最佳实践

* 使用缓存
```text
FROM ubuntu
MAINTAINER Michael Crosby <michael@crosbymichael.com>
RUN echo "deb http://archive.ubuntu.com/ubuntu precise main universe" > /etc/apt/sources.list
RUN apt-get update
RUN apt-get upgrade -y

```
```text
Dockerfile的每条指令都会将结果提交为新的镜像，下一个指令将会基于上一步指令的镜像的基础上构建
更改MAINTAINER指令会使Docker强制执行RUN指令来更新apt，而不是使用缓存。
```




* 参考指南:

[docker最佳实践](https://blog.docker.com/2019/07/intro-guide-to-dockerfile-best-practices/)