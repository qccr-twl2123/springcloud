### store


#### 使用背景
```text
当你打算开发大型单页应用（SPA），会出现多个视图组件依赖同一个状态，
来自不同视图的行为需要变更同一个状态。

遇到以上情况时候，你就应该考虑使用Vuex了，它能把组件的共享状态抽取出来，当做一个全局单例模式进行管理。
这样不管你在何处改变状态，都会通知使用该状态的组件做出相应修改。

```

* 定义
```vue
import Vue from 'vue';
import Vuex form 'vuex';

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        count: 0
    },
    mutations: {
        increment (state) {
            state.count++
        }
    }
})
```

* 更新单例模式的值
```vue
store.commit('increment');

console.log(store.state.count)  // 1
```

### Getters对象

* 如果我们需要对state对象进行做处理计算，如下：
```vue
computed: {
    doneTodosCount () {
        return this.$store.state.todos.filter(todo => todo.done).length
    }
}
```
```text
如果多个组件都要进行这样的处理，那么就要在多个组件中复制该函数。
这样是很没有效率的事情，当这个处理过程更改了，还有在多个组件中进行同样的更改，这就更加不易于维护。
```

* Vuex中getters对象，可以方便我们在store中做集中的处理。Getters接受state作为第一个参数：
```vue
const store = new Vuex.Store({
  state: {
    todos: [
      { id: 1, text: '...', done: true },
      { id: 2, text: '...', done: false }
    ]
  },
  getters: {
    doneTodos: state => {
      return state.todos.filter(todo => todo.done)
    }
  }
})
```
* 在Vue中通过store.getters对象调用。
```vue
computed: {
  doneTodos () {
    return this.$store.getters.doneTodos
  }
}
```