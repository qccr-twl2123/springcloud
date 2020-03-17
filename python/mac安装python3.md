### python3安装

[安装包下载地址](https://www.python.org/downloads/release/python-380/)

* 检测安装路径
```text
终端运行:open ~/.bash_profile,会看到如下:

# Setting PATH for Python 3.8
# The original version is saved in .bash_profile.pysave
PATH="/Library/Frameworks/Python.framework/Versions/3.8/bin:${PATH}"
export PATH

```

* 刷新配置
```text
source ~/.bash_profile
```

* 查看包安装路径
```text
1.终端输入:python或python3
2. from distutils.sysconfig import get_python_lib
3. print(get_python_lib())
```


