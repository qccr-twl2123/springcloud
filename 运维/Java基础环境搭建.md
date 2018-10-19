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

```
* git 安装


[mysql安装教程](https://www.linuxidc.com/Linux/2016-09/135288.htm)
