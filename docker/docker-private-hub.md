### docker 私有仓库搭建
* 私有库搭建
```text
1.下载镜像
  docker pull registry
2.启动容器
  docker run -d -p 5000:5000 --restart=always --name=registry-srv -v /mydata/dockerRegistry:/var/lib/registry registry
  参数解释:
     -d：后台运行
     -p：将容器的5000端口映射到宿主机的5000端口
     --restart：docker服务重启后总是重启此容器
     --name：容器的名称
     -v：将容器内的/var/lib/registry映射到宿主机的/mydata/dockerRegistry目录
3.验证
   http://ip:5000/v2/_catalog
4.指定私有库
   执行: vi /etc/docker/daemon.json
   新增: "insecure-registries":["47.105.131.1:5000"]      
```
* 私有库web页面
```text
1.下载镜像
    docker pull hyper/docker-registry-web
2.启动容器
    docker run -it -p 8080:8080 --restart=always --name registry-web --link registry-srv -e REGISTRY_URL=http://registry-srv:5000/v2 -e REGISTRY_NAME=localhost:5000 hyper/docker-registry-web
    参数解释:
         -it: 以交互模式运行
         --link：链接其它容器(registry-srv)，在此容器中，使用registry-srv等同于registry-srv容器的局域网地址
         -e：设置环境变量
```

* 上传镜像到私有库
```text
1. 先将镜像重新命名: ip:5000/<imageName>
   docker tag cbbing/hcharts 192.168.1.87:5000/cbbing/hcharts
2. docker push <重命名后的镜像>
```