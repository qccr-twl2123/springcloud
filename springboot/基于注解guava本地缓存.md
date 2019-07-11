### springboot 整合本地缓存 

```text
在如今高并发的互联网应用中，缓存的地位举足轻重，对提升程序性能帮助不小。
而 3.x开始的 Spring也引入了对 Cache的支持，那对于如今发展得如火如荼的 Spring Boot来说自然也是支持缓存特性的。
当然 Spring Boot默认使用的是 SimpleCacheConfiguration，即使用 ConcurrentMapCacheManager 来实现的缓存。
但本文将讲述如何将 Guava Cache缓存应用到 Spring Boot应用中。

```

```text
Guava Cache是一个全内存的本地缓存实现，而且提供了线程安全机制，所以特别适合于代码中已经预料到某些值会被多次调用的场景
```

* springboot 项目添加 maven 依赖、
```xml
 <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!--for mybatis-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.2</version>
        </dependency>

        <!--for Mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Spring boot Cache-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>

        <!--for guava cache-->
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>27.0.1-jre</version>
        </dependency>

    </dependencies>
```
### 建立 Guava Cache配置类
* 引入 Guava Cache的配置文件 GuavaCacheConfig
```java
@Configuration
@EnableCaching
public class GuavaCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheBuilder(
                CacheBuilder.newBuilder().
                        expireAfterWrite(10, TimeUnit.SECONDS).
                        maximumSize(1000));
        return cacheManager;
    }
}
```

*  service 层代码编写
```java
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getUsers() {
        return userMapper.getUsers();
    }

    public int addUser( User user ) {
        return userMapper.addUser(user);
    }

    @Cacheable(value = "user", key = "#userName")
    public List<User> getUsersByName( String userName ) {
        List<User> users = userMapper.getUsersByName( userName );
        System.out.println( "从数据库读取，而非读取缓存！" );
        return users;
    }
}
```
* 注解说明
```text
看得很明白了，我们在 getUsersByName接口上添加了注解：@Cacheable。这是 缓存的使用注解之一，除此之外常用的还有 @CachePut和 @CacheEvit，分别简单介绍一下：


@Cacheable：配置在 getUsersByName方法上表示其返回值将被加入缓存。同时在查询时，会先从缓存中获取，若不存在才再发起对数据库的访问

@CachePut：配置于方法上时，能够根据参数定义条件来进行缓存，其与 @Cacheable不同的是使用 @CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中，所以主要用于数据新增和修改操作上

@CacheEvict：配置于方法上时，表示从缓存中移除相应数据。

```

* 主类代码编写
```java
@SpringBootApplication
@MapperScan("cn.codesheep.springbt_guava_cache")
@EnableCaching
public class SpringbtGuavaCacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbtGuavaCacheApplication.class, args);
    }
}

```

#### Key的配置

* 基本形式
```java
@Cacheable(value="cacheName", key"#id")
public ResultDTO method(int id);
```

* 组合形式
```java
@Cacheable(value="cacheName", key"T(String).valueOf(#name).concat('-').concat(#password))
public ResultDTO method(int name, String password);
```

* 对象形式
```java
@Cacheable(value="cacheName", key"#user.id)
public ResultDTO method(User user);
```

* 自定义KEY生成机制(多参数、原子类型数组、方法名识别 等问题)
```java

@Cacheable(value="gomeo2oCache", keyGenerator = "keyGenerator")
public ResultDTO method(User user);
```

```java
class CacheKeyGenerator implements KeyGenerator {
 
    // custom cache key
    public static final int NO_PARAM_KEY = 0;
    public static final int NULL_PARAM_KEY = 53;
    
    @Override
    public Object generate(Object target, Method method, Object... params) {
 
        StringBuilder key = new StringBuilder();
        key.append(target.getClass().getSimpleName()).append(".").append(method.getName()).append(":");
        if (params.length == 0) {
            return key.append(NO_PARAM_KEY).toString();
        }
        for (Object param : params) {
            if (param == null) {
                log.warn("input null param for Spring cache, use default key={}", NULL_PARAM_KEY);
                key.append(NULL_PARAM_KEY);
            } else if (ClassUtils.isPrimitiveArray(param.getClass())) {
                int length = Array.getLength(param);
                for (int i = 0; i < length; i++) {
                    key.append(Array.get(param, i));
                    key.append(',');
                }
            } else if (ClassUtils.isPrimitiveOrWrapper(param.getClass()) || param instanceof String) {
                key.append(param);
            } else {
                log.warn("Using an object as a cache key may lead to unexpected results. " +
                        "Either use @Cacheable(key=..) or implement CacheKey. Method is " + target.getClass() + "#" + method.getName());
                key.append(param.hashCode());
            }
            key.append('-');
        }
 
        String finalKey = key.toString();
        long cacheKeyHash = Hashing.murmur3_128().hashString(finalKey, Charset.defaultCharset()).asLong();
        log.debug("using cache key={} hashCode={}", finalKey, cacheKeyHash);
        return key.toString();
    }
}
```


* 参考文档
[springboot 整合guava cache](https://www.jianshu.com/p/921c588289c7)
[Cacheable key生成策略](https://blog.csdn.net/syani/article/details/52239967)
