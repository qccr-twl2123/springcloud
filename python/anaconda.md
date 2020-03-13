### Anaconda 常用命令

#### CentOS7 安装Anaconda
```text
1. 构建目录 
    cd /usr/local
    mkdir anaconda
    cd anaconda
2. 下载清华镜像
   wget https://mirrors.tuna.tsinghua.edu.cn/anaconda/archive/Anaconda3-2019.03-Linux-x86_64.sh
   补充: yum -y install wget

3. 执行安装命令
   bash Anaconda3-2019.03-Linux-x86_64.sh
   
   出错补充执行: yum install -y bzip2
   
4. 配置环境变量
   vi /etc/profile
   
   PATH=/root/anaconda3/bin:$PATH
   export PATH
   
   source ~/.bashrc

5. 验证
   source activate # 进入conda环境 出现(base)则说明安装成功
   source deactivate # 退出conda环境


6. 配置下载源- 清华
 conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/

 
```

* 安装python3
```text
conda search python
conda create --name python37 python=3.7
source activate python37   #激活python37
source deactivate    #返回默认的python 2.7环境
```

[清华anaconda镜像下载地址](https://mirrors.tuna.tsinghua.edu.cn/anaconda/archive/?C=M&O=D)
[Linux-Centos7下安装Anaconda（2019年新版）](https://zhuanlan.zhihu.com/p/64930395)

#### 常用命令
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

#### idea 构建anaconda环境





