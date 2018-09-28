### docker rabbitmq 教程

* 安装
```text
docker run -d --name myrabbitmq -p 5673:5672 -p 15673:15672 docker.io/rabbitmq:3-management
管理页面: http://ip:15367  默认:guest
```
* 进入容器终端
```text
docker exec -it rabbitmq /bin/bash
```