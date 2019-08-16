###  Vue 变量相关知识点总结

#### 全局变量使用
* 定义方式一: js文件
```text
let a = "mark";
export default a
```
* 定义方式二: vue文件
```text
<script>
const name = 'shenxianhui';
const age = 24;
 
export default {
    name,
    age
};
</script>
```
* 使用方式，在main.js文件中引入(推荐用法)
```text
1. 先挂载 
import myStore from '@/components/Store'; 

2. 在定义
Vue.prototype.$store = myStore; //修改原型链

如此设置就不需要引用,this.$store.name直接使用
```


