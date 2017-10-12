### Guava 实现本地缓存


```java
import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存
 * Created by xierongli on 17/7/29.
 */
public class LocalCache {

    private static Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(100).expireAfterWrite(10, TimeUnit.MINUTES)
            .recordStats()
            .build();

    //取到的是json数据
    public static String get(String key)  {
        String var = null;
        try {
            var = cache.get(key, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "";
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "";
        }
        return var;
    }
    public static void put(String key, Object value) {
        //将object转存json存储
        cache.put(key, JSON.toJSONString(value));
    }

}

```



