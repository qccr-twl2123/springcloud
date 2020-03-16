### 面试问题
```text
1. spring bean 的生命周期?
2. spring Ioc 后置处理器?
3. 循环依赖?
4. 向spring容器中注入bean的方式有哪几种？
```

* 自我认知
```text
Java中一切皆对象,Spring一切皆Bean.

依赖注入,控制反转, 本质是一个大Map,存储单例子象于内存

生命周期: 实例化->属性填充->对象化->销毁

后置处理器可以对SpringIoc进行扩展:
    BeanPostProcessor 在Bean对象初始化进行扩展,
    BeanFactoryPostProcessor 在Bean实lihua

```
* 常用接口
```text

```
* 常用后置处理器
```text

```

#### 生命周期
```text
实例化->属性填充->对象化->销毁
```

#### 往spring容器中注入Bean的方式
```text

```