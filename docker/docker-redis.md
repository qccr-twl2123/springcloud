### docker redis 使用教程
* 安装
```text
docker pull  redis:3.2
```
* 运行
```text
docker run -p 6379:6379 -v $PWD/data:/data  -d redis:3.2 redis-server --appendonly yes

参数解释:
redis-server --appendonly yes : 在容器执行redis-server启动命令，并打开redis持久化配置
```
* redis-cli(客户端)
```text
docker exec -it  <容器ID> redis-cli
```