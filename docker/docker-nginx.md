### docker nginx 教程

* 安装运行
```text
1.安装
docker pull nginx

2.运行
docker run -p 80:80 -v $PWD/conf.d/default.conf:/etc/nginx/conf.d/default.conf -v $PWD/conf.d:/etc/nginx/conf.d -v $PWD/conf.d:/conf.d -v $PWD/www:/usr/share/nginx/html -v $PWD/conf/nginx.conf:/etc/nginx/nginx.conf -v $PWD/logs:/wwwlogs  -d nginx 
参数解释:
  -v 将当前目录挂在到容器相应目录

注:
 宿主机切换到nginx映射目录
  conf 手动创建nginx.conf
      
```