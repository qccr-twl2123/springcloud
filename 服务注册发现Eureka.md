### SpringCloud服务注册与发现Eureka


![输入图片说明](https://github.com/qccr-twl2123/springcloud/blob/master/images/springcloud架构.png "在这里输入图片标题")

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