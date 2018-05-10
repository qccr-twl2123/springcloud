### JVM 调优


![输入图片说明](https://github.com/qccr-twl2123/livtrip/blob/master/src/main/resources/static/resources/images/JVM调优.png "在这里输入图片标题")

![输入图片说明](https://github.com/qccr-twl2123/livtrip/blob/master/src/main/resources/static/resources/images/spring-boot-spring-cloud-memory-5.png "在这里输入图片标题")

#### JVM 内存模型及垃圾回收算法
```
1.JVM内存划分
堆(heap)区:
New Generation(新生代)
Eden Space伊甸园
Survivor Space(幸存者区)
Tenured Gen（老年代-养老区）


非堆(heap)区:
Code Cache(代码缓存区)
Perm Gen(永久代):类、方法的定义
Jvm Stack(java虚拟机栈):方法参数、局域变量等的引用,方法执行顺序按照栈的先入后出方式
Local Method Statck(本地方法栈)

2.垃圾回收算法(分代回收and标记清除)
```
#### OOM内存溢出定位
```
首先:确认服务是否正常
tomcat进程判定
ps aux | grep tomcat

如果是调用了外部服务,请使用curl进行判定.
一、get请求
curl "http://www.baidu.com"  如果这里的URL指向的是一个文件或者一幅图都可以直接下载到本地
curl -i "http://www.baidu.com"  显示全部信息
curl -l "http://www.baidu.com" 只显示头部信息
curl -v "http://www.baidu.com" 显示get请求全过程解析

wget "http://www.baidu.com"也可以

二、post请求
curl -d "param1=value1&param2=value2" "http://www.baidu.com"


1.查看内存分配情况:
  jmap -heap 10765(进程ID)
  
```
 ![输入图片说明](https://github.com/qccr-twl2123/springcloud/blob/master/images/jmap堆的使用情况.png "在这里输入图片标题")

```
2.找到最耗内存的对象:
  jmap -histo:live 10765 | more
```  
  
  ![输入图片说明](https://github.com/qccr-twl2123/livtrip/blob/master/src/main/resources/static/resources/images/对象内存消耗.png "在这里输入图片标题")

```
3.确认资源是否耗尽:
  pstree
  netstat
  
  查看线程数: ll /proc/${PID}/task
  句柄详情:   ll /proc/${PID}/fd
  
```
#### OOM内存溢出解决方案
springboot:
```
#nohup mvn spring-boot:run > ../log 2>&1 &
export MAVEN_OPTS="-Xms512m -Xmx2048m -XX:PermSize=256m -XX:MaxPermSize=512m"
nohup mvn clean spring-boot:run | tee ../log 2>&1 &

//这句代码设置发布模块内存分布情况
export MAVEN_OPTS="-Xms512m -Xmx2048m -XX:PermSize=256m -XX:MaxPermSize=512m"
参数说明:
-Xmx1024m：设置JVM最大可用内存为1024M。
-Xms512m：虚拟机占用系统的最小内存。此值可以设置与-Xmx相同，以避免每次垃圾回收完成后JVM重新分配内存。
-XX:MaxPermSize：最大堆大小。这个也适当大些, 所以若出现问题，首先请调整 –Xms512m：将其设置的小一些
```
tomcat:


参考博客:
```
线上服务溢出
https://mp.weixin.qq.com/s?__biz=MjM5ODYxMDA5OQ==&mid=2651960342&idx=1&sn=9b2dbbb2cfd7710f25be1a0862a9b2be&chksm=bd2d01ca8a5a88dcc14608cb00e0dbde11869d053ee8c83bc96e7b4a0fbd71d28d7fbb009c98&mpshare=1&scene=23&srcid=09089nNk4emn8gVQCDfRqa7g#rd

JVM调优总结
http://www.cnblogs.com/andy-zhou/p/5327288.html

Spring Boot & Spring Cloud应用内存管理
http://blog.didispace.com/spring-boot-spring-cloud-memory/
JVM性能调优
https://www.oschina.net/question/3189620_2269580
```


