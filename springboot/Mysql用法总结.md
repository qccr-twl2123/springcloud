### Mysql 用法总结
```text
1.建表规范
2.函数使用
3.索引运用
```

```text
地址
192.168.10.190
帐号：
hugaoxiang 
密码
a7M6031XxUi5Z91ss8OB 
```

#### 建表规范
* 建表公共字段
```mysql
ALTER TABLE `xxx`
ADD COLUMN   `create_person` varchar(64) NOT NULL DEFAULT 'system' COMMENT '创建人',
ADD COLUMN  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
ADD COLUMN  `update_person` varchar(64) NOT NULL DEFAULT 'system' COMMENT '更新人',
ADD COLUMN  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间'

# 投融家处理方案
ALTER TABLE `xxx`
ADD COLUMN   `create_person` varchar(64) NOT NULL DEFAULT 'system' COMMENT '创建人',
ADD COLUMN  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
ADD COLUMN  `update_person` varchar(64) NOT NULL DEFAULT 'system' COMMENT '更新人',
ADD COLUMN  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'


ALTER TABLE `xxx`
ADD COLUMN   `create_person` varchar(64) NOT NULL DEFAULT 'system' COMMENT '创建人',
ADD COLUMN  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
ADD COLUMN  `update_person` varchar(64) NOT NULL DEFAULT 'system' COMMENT '更新人',
ADD COLUMN  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'

```


#### 函数使用
* 条件判断函数
```text
1. IF(expr,v1,v2)函数: 如果表达式expr成立，返回结果v1；否则，返回结果v2。
eg:
SELECT IF(1 > 0,'正确','错误')    
->正确

2. IFNULL(v1,v2)函数: 　如果v1的值不为NULL，则返回v1，否则返回v2。
eg:
SELECT IFNULL(null,'Hello Word')
->Hello Word

3.　CASE表示函数开始，END表示函数结束。如果e1成立，则返回v1,如果e2成立，则返回v2，
    当全部不成立则返回vn，而当有一个成立之后，后面的就不执行了。
CASE 
　　WHEN e1
　　THEN v1
　　WHEN e2
　　THEN e2
　　...
　　ELSE vn
END
```
* 加密函数
```text
1. PASSWORD(str)该函数可以对字符串str进行加密，一般情况下，PASSWORD(str)用于给用户的密码加密。
eg: SELECT PASSWORD('123')
    ->*23AE809DDACAF96AF0FD78ED04B6A265E05AA257
2.MD5(str)函数可以对字符串str进行散列，可以用于一些普通的不需要解密的数据加密。
eg: SELECT md5('123')
    ->202cb962ac59075b964b07152d234b70   
    
```
* 格式化函数
```text
1. FORMAT(x,n)函数可以将数字x进行格式化，将x保留到小数点后n位。
eg: SELECT FORMAT(3.1415926,3)
    ->3.142
2.IP地址与数字相互转换的函数
  INET_ATON(IP)函数可以将IP地址转换为数字表示；IP值需要加上引号；
  INET_NTOA(n)函数可以将数字n转换成IP形式。
eg:
SELECT INET_ATON('192.168.0.1')
    ->3232235521
SELECT INET_NTOA(3232235521)
    ->192.168.0.1
```


