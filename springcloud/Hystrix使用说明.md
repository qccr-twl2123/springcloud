#### Hystrix使用说明，配置参数说明

* 示例
```text
1.用于类上的注释
@DefaultProperties(groupKey = "TuiActivityCenter", threadPoolKey = "RemoteAdxSuccBidServiceImpl",
        commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")},
        threadPoolProperties = {@HystrixProperty(name="coreSize",value="300"), @HystrixProperty(name="maximumSize",value="500")})
        
2.方法级别的注释写法

@HystrixCommand(commandKey = "getAdxSuccBidInfo", fallbackMethod = "getAdxSuccBidInfoFallback")
```

* 参考文档
[Hystrix使用说明](https://blog.csdn.net/tongtong_use/article/details/78611225)
