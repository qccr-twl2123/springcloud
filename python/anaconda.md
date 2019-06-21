### Anaconda 常用命令
-[x] 环境变量配置
```text
如果是windows的话需要去 控制面板\系统和安全\系统\高级系统设置\环境变量\用户变量\PATH 中添加 anaconda的安装目录的Scripts文件夹, 
比如我的路径是D:\Software\Anaconda\Scripts
```
-[x] 设置国内镜像仓库


-[x] 查看环境
```text
conda env list
```
-[x] 切换环境
```text
activate python32
```

-[x] 创建环境
```text
conda create -n learn python=3
```
-[x] 导入导出环境
```text
conda env export > environment.yaml
conda env create -f environment.yaml
```

* 总结
```text
activate  // 切换到base环境
activate learn // 切换到learn环境
conda create -n learn python=3  // 创建一个名为learn的环境并指定python版本为3(的最新版本)
conda env list // 列出conda管理的所有环境
conda list // 列出当前环境的所有包
conda install requests 安装requests包
conda remove requests 卸载requets包
conda remove -n learn --all // 删除learn环境及下属所有包
conda update requests 更新requests包
conda env export > environment.yaml  // 导出当前环境的包信息
conda env create -f environment.yaml  // 用配置文件创建新的虚拟环境
```
