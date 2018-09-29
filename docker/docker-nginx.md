### docker nginx 教程

* 安装运行
```text
1.安装
docker pull nginx

2.运行
docker run -p 80:80 -v $PWD/conf.d/default.conf:/etc/nginx/conf.d/default.conf -v $PWD/conf.d:/etc/nginx/conf.d -v $PWD/conf.d:/conf.d -v $PWD/www:/usr/share/nginx/html -v $PWD/conf/nginx.conf:/etc/nginx/nginx.conf -v $PWD/logs:/wwwlogs  -d nginx 
参数解释:
-v $PWD/conf.d/default.conf:/etc/nginx/conf.d/default.conf
-v $PWD/conf.d:/etc/nginx/conf.d 
-v $PWD/conf.d:/conf.d 
-v $PWD/www:/usr/share/nginx/html 
-v $PWD/conf/nginx.conf:/etc/nginx/nginx.conf 
-v $PWD/logs:/wwwlogs 

注: 挂载文件需要在当前目录先进行创建,否则run运行不起来.    
```