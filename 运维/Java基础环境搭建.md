### Java 项目基础环境搭建
* jdk 安装
```text
进入官网
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
下载jdk
wget http://download.oracle.com/otn-pub/java/jdk/8u191-b12/2787e4a523244c269598db4e85c51e0c/jdk-8u191-linux-x64.tar.gz

创建安装目录
mkdir /usr/local/java/

解压到安装目录
tar -zxvf jdk-8u171-linux-x64.tar.gz -C /usr/local/java/

设置环境变量
vim /etc/profile

export JAVA_HOME=/usr/local/java/jdk1.8.0_171
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH

刷新环境变量
source /etc/profile

添加软连接
ln -s /usr/local/java/jdk1.8.0_171/bin/java /usr/bin/java

```
* maven 安装
```text
1.下载
http://maven.apache.org/download.cgi
wget http://mirror.bit.edu.cn/apache/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz

vi /etc/profile然后还需要 配置环境变量。
#在适当的位置添加
export M2_HOME=/usr/local/maven3
export PATH=$PATH:$JAVA_HOME/bin:$M2_HOME/bin
 
保存退出后运行下面的命令使配置生效，或者重启服务器生效。
source /etc/profile
 
验证版本
mvn -v
出现maven版本即成功

```
* git 安装
```text
yum install -y git
yum remove git
git --version
```


[mysql安装教程](https://www.linuxidc.com/Linux/2016-09/135288.htm)
[git安装](https://www.cnblogs.com/liaojie970/p/6253404.html)
