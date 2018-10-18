### docker 数据管理
```text
docker 中有两个与数据卷相关的参数
-v: 创建一个挂载地址,[host-dir]:[container-dir]:[rw|ro]
-volumes-from: mount all volumes from a given container
```
* 创建方式
```text
1. 直接在容器内部创建,不指定host-dir(没有引用指向,host目录也会删除)
docker run -v /test1

2.挂载Host目录作为数据卷 
docker run -v /data/volumes:/volumes <ImageName> /bin/bash
 
```