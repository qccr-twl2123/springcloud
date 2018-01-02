### Mysql 用法总结
```text
1.建表规范
2.函数使用
3.索引运用
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
```
