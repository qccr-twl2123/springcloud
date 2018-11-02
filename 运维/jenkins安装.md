### jenkins 安装

* 下载jenkins 资源
```text
https://jenkins.io/download/
wget http://updates.jenkins-ci.org/download/war/2.130/jenkins.war
2. 运行
java -jar jenkins.war --httpPort=8088&
3. 初始化页面管理密码
find / -name initialAdminPassword
cat /var/lib/jenkins/secrets/initialAdminPassword(以上面查找到为准)
```

* 忘记admin用户登陆密码
```text
find / -name initialAdminPassword
cat /var/lib/jenkins/secrets/initialAdminPassword
```

![jenkins安装教程](https://my.oschina.net/xshuai/blog/1837180)
