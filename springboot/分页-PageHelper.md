### SpringBoot集成PageHelper

* pom.xml maven 依赖
```xml
<!--分页插件-->
<dependency>
      <groupId>com.github.pagehelper</groupId>
      <artifactId>pagehelper-spring-boot-starter</artifactId>
      <version>1.0.0</version>
</dependency>

```

* 调用指南
```java
class A{
    public PageInfo<FoursStoreBrand>  queryBrandPage(FoursStoreBrand brand,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize); //默认进行count 计算
        //其他写法
         //推荐: true 表示是否进行count 计算, false 表示是否进行分页合理化. 
         //PageHelper.startPage(pageNum,pageSize,true,false); 
        //对排序的支持： PageHelper.startPage(pageNum,pageSize,"create_time desc");
        List<FoursStoreBrand> brandList = brandMapper.queryFoursStoreBrand(brand);
        return new PageInfo<>(brandList);
    }

}

```
* 参考博客
```
PageHepler 使用教程
https://gitee.com/free/Mybatis_PageHelper/
```
