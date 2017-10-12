### AOP 实现日志打印功能

* maven 依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
* AOP 切面日志打印类
```java
@Aspect
@Component
public class ControllerAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    @Pointcut("execution(public * com.trj.jk.web.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Map<String,String> parameterMap = MapUtil.getParameterMap(request);
        logger.info("接口请求URL:{} 参数:{}",request.getRequestURL().toString(),JSON.toJSONString(parameterMap));
    }

    @AfterReturning(returning = "object", pointcut = "webLog()")
    public void doAfterReturning(Object object) throws Throwable {
        logger.info("耗时:{} 接口调用返回:{} ",System.currentTimeMillis() - startTime.get(),JSON.toJSONString(object));
    }
}


```
