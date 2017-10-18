### SpringCloud服务注册与发现Eureka

#### 构建Eureka步骤
```
1. maven依赖:

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka-server</artifactId>
</dependency>

2. application.properties:

server.port=1114
#eureka.instance.hostname=localhost
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
eureka.client.serviceUrl.defaultZone=http://localhost:${server.port}/eureka/

```

通过@EnableEurekaServer注解启动一个服务注册中心提供给其他应用进行对话。
这一步非常的简单，只需要在一个普通的Spring Boot应用中添加这个注解就能开启此功能，比如下面的例子：
```java
@EnableEurekaServer
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                    .web(true).run(args);
    }
}
```

为了与后续要进行注册的服务区分，这里将服务注册中心的端口通过server.port属性设置为1001。
启动工程后，访问：http://localhost:1001/，可以看到下面的页面，其中还没有发现任何服务。

![输入图片说明](https://github.com/qccr-twl2123/springcloud/blob/master/images/spring-cloud-starter-dalston-1-1.png "在这里输入图片标题")



