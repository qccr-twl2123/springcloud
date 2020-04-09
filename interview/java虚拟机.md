### 字节码的编译原理

#### Java文件->class文件
````text
1.词法分析: 将Java关键词和符号转换成token序列
2.语法分析: 将token序列解析成抽象语法树
3.语义分析: 完善语法的合法性,诸如追加无参构造函数,合并String字符串
4.生成字节码: 

以上四个步骤将符合Java语法规范的Java代码转换成符合JVM规范的字节码.
````

#### javap 工具分析字节码

```text
格式: javap <options> <classes>

参数: 
      -help  --help  -?        输出此用法消息
      -version                 版本信息，其实是当前javap所在jdk的版本信息，不是class在哪个jdk下生成的。
      -v  -verbose             输出附加信息（包括行号、本地变量表，反汇编等详细信息）
      -l                         输出行号和本地变量表
      -public                    仅显示公共类和成员
      -protected               显示受保护的/公共类和成员
      -package                 显示程序包/受保护的/公共类 和成员 (默认)
      -p  -private             显示所有类和成员
      -c                       对代码进行反汇编
      -s                       输出内部类型签名
      -sysinfo                 显示正在处理的类的系统信息 (路径, 大小, 日期, MD5 散列)
      -constants               显示静态最终常量
      -classpath <path>        指定查找用户类文件的位置
      -bootclasspath <path>    覆盖引导类文件的位置
      
一般常用的是-v -l -c三个选项。
javap -v classxx，不仅会输出行号、本地变量表信息、反编译汇编代码，还会输出当前类用到的常量池等信息。
javap -l 会输出行号和本地变量表信息。
javap -c 会对当前class字节码进行反编译生成汇编代码。
      
```
