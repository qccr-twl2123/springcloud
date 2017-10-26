### Mysql连接超时

### 异常信息
```text
No operations allowed after connection closed.; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException:
No operations allowed after connection closed.


```
### 原因分析
```text
看报的异常信息是应用程序注册了JDBC驱动，
但当程序停止时无法注销这个驱动，tomcat为了防止内存溢出，就给强制注销了。

当应用程序和数据库建立连接时，如果超过了8个小时，应用程序句不会去访问数据库，
数据库就会出现断掉连接的现象 。这时再次访问就会抛出异常。
```

### 解决方案
```text
1.mysql4 以前版本
在数据库连接字符串中增加“autoReconnect=true ”选项

2.my.ini文件中增加此参数：
interactive_timeout=288000
wait_timeout=288000

```


参考博客:
```

```


