### pandas

- [x] loc
```text

```

- [x] iloc

```text
iloc 是基于“位置”的Dataframe的操作，即主要基于下标的操作
Pandas中的 iloc 是用基于整数的下标来进行数据定位/选择
iloc 的语法是 data.iloc[<row selection>, <column selection>],  
iloc 在Pandas中是用来通过数字来选择数据中具体的某些行和列。你可以设想每一行都有一个对应的下标（0,1,2，...)，
通过 iloc 我们可以利用这些下标去选择相应的行数据。同理，对于行也一样，想象每一列也有对应的下标(0,1,2,...),通过这些下标也可以选择相应的列数据。
```
```python
  # 使用DataFrame 和 iloc 进行单行/列的选择
    # 行选择：
    data.iloc[0] # 数据中的第一行
    data.iloc[1] # 数据中的第二行
    data.iloc[-1] # 数据中的最后一行
    
    # 列选择：
    data.iloc[:, 0] # 数据中的第一列
    data.iloc[:, 1] # 数据中的第二列
    data.iloc[:, -1] # 数据中的最后一列 

 # 使用 iloc 进行行列混合选择
    data.iloc[0:5] # 数据中的第 1-5 行
    data.iloc[:, 0:2] # 选择数据中的前2列和所有行
    data.iloc[[0, 3, 6, 24], [0, 5, 6]] # 选择第 1,4,7,25行 和 第 1,6,7 列
    data.iloc[0:5, 5:8] # 选择第1-6行 和 6-9列
```


- [x] 参考资料

[pandas iloc 和 loc区别](https://www.jianshu.com/p/d6a9845a0a34)
