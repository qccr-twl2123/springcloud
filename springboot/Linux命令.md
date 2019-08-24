### Linux 命令

* 从服务器上下载文件命令:scp
```text
scp root@192.168.10.88:/opt/pdf_template/term/*.pdf ./

scp root@47.75.85.48:/backup/storeServer.zip ./

scp root@47.75.85.48:/backup/door-server-demo.zip ./

```
* curl 
```text
curl -i --get --include 'http://jisucxdq.market.alicloudapi.com/car/brand'  -H 'Authorization:APPCODE 你自己的AppCode'
```

* telnet 
```text
测试服务器端口是否可以联通
telnet 10.113.12.233 1099
```

* 查看远程服务器方法 入参和出参
```text
-- ./greys.sh 1 使用
curl -sLk http://ompc.oss.aliyuncs.com/greys/install.sh|sh

./greys.sh 1

watch -bf <方法reference> <方法名> params -x 1

watch -bf <方法reference> <方法名> returnObj -x 1

# json信息
watch -s <方法reference> <方法名>returnObj -x 4

watch -s <方法reference> <方法名> params -x 4   
```
* 排查JVM 堆中内存&GC情况
```text
查看堆中存活的对象
jmap -histo:live <pid> 

查询进程的GC情况
jstat -gcutil 1 100 
jstat -gcutil <pid> 打印的时间间隔数  
```



