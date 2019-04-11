### vue 学习札记

* 官网地址
[vue官网](https://cn.vuejs.org)

### 常用标签
```text
{{...}}读取data中字段
v-if, v-if-else, v-else 判断字段false or true 显示内容
v-for 循环, 以html标签为载体
v-model: 实现数据双向绑定. <input name='tag' v-model='test'/> {{test}}
v-on 或@click
v-bind或: 绑定html元素属性的值
v-html
v-show
v-on(event) 事件处理
```
### 生命周期
```text
created 组件初始化，且并未挂载元素
mounted 页面元素已经挂载
beforeDestory 实例销毁
```
### 重要属性
* computed:计算属性
```text
计算属性是基于它们的依赖进行缓存的，而方法是不会基于它们的依赖进行缓存的
重点
1. 计算属性可以直接定义而不需要在data 中声明
2. 适用于多个变量或对象进行处理得出一个值. 例如购物车列表中商品数量的变化

```

* filter:过滤属性
```text
支持串联使用, 支持传递参数 eg: {{msg | testFilter}}

<p>{{message | sum}}</p>
<p>{{message | cal 10 20}}</p>  <!--过滤器函数始终以表达式的值作为第一个参数。带引号的参数视为字符串，而不带引号的参数按表达式计算。-->
<p>{{message | sum | currency }}</p> <!--添加两个过滤器,注意不要冲突-->


```

* watcher:观察属性
```text
观测的变量必须在data中声明

```

### 组件知识

* 组件引用三板斧 
```text
引入: 引入一个子组件的地址
应用: 在当前父组件中声明子组件
挂载: 向子组件传递参数或挂载子组件的回调函数监听
```

#### 父组件传参子组件
```text
1. 

```


