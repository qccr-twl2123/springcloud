### SpringBoot集成Mybatis-Paginator
```
mybatis-paginator是gethub上的一个开源项目、用于java后台获取分页数据、
该开源项目还提供一个列表组件（mmgrid）用于前端展示。
该开源项目地址：https://github.com/miemiedev
```
* Maven中加入依赖：
```xml
<dependencies>  
    <dependency>  
        <groupId>com.github.miemiedev</groupId>  
        <artifactId>mybatis-paginator</artifactId>  
        <version>1.2.10</version>  
    </dependency>  
</dependencies>
```
* mybatis-config.xml 设置分页插件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<plugins>
		<!-- <plugin interceptor="com.trj.mybatis.plugin.PageInterceptor" /> -->
    	<plugin interceptor="com.github.miemiedev.mybatis.paginator.OffsetLimitInterceptor">             
    		<property name="dialectClass" value="com.github.miemiedev.mybatis.paginator.dialect.MySQLDialect"/>         
    	</plugin> 
	</plugins>
</configuration>
```
* 调用方式(分页加多列排序)
```
int page = 1; //页号
int pageSize = 20; //每页数据条数
String sortString = "age.asc,gender.desc";//如果你想排序的话逗号分隔可以排序多列

PageBounds pageBounds = newPageBounds(page, pageSize , Order.formString(sortString));

List list = findByCity("BeiJing",pageBounds);

//获得结果集条总数,返回的数值是PageList,而它实现List接口,所以进行强转 
PageList pageList = (PageList)list;
System.out.println("totalCount: "+ pageList.getPaginator().getTotalCount());

```

#### 封装使用教程
* controller
```java

@Controller
@RequestMapping(value = "/acl/role")
public class RoleController {
    /**
     * 
     * @param queryDto  查询Dto
     * @param page      当前页码
     * @param limit     每页最大记录数
     * @param sortStr   排序字段
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    Page<Role> list(RoleQueryDto queryDto, 
            @RequestParam (value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value= "sort", required = false) String sortStr){
        return roleService.findPage(queryDto, MyPage.create(page, limit, sortStr));
    }
}

public class RoleServiceImpl implements RoleService {
        public Page<Role> findPage(RoleQueryDto queryDto, MyPage page) {
        return roleDao.selectByRoleDto(queryDto, page.getRowBounds()).getPage();
    }
}
```
* 请求方式
```
当你请求后台: acl/role/list时, 会默认给后台传 
“page:xxx”, 
“limit:xxx”, 
“sort:createdDate.asc” // 需要排序的字段及排序方式
```
* MyPage类:动态封装sql
```java
/**
* PageReqeust :工厂模式,作用是生产MyPage对象
*/
public class MyPage extends PageRequest {
    // 构造方法
    public MyPage(int page, int size, String sort) {
        super(page, limit, sort);
    }
    // 静态方法,创建MyPage对象
    public static MyPage create(Integer page, Integer size, String sort) {
        return new MyPage(page, size, sort);
    }

    public RowBounds getRowBounds() {
    // 使用PageBounds这个对象来控制结果的输出
        PageBounds bounds = new PageBounds(this.getPageNumber() + 1, 
                    this.getPageSize(), PageOrder.formString(this.getSort()), true);
        return bounds;
    }
}

/**
* MyPage对象工厂
* Pageable 接口定义 常规的分页方法
*/
public class PageRequest implements Pageable, Serializable{
    private int page;
    private int size;
    private String sort;

    public PageRequest(int page, int size, String sort) {

        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        }

        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }

        this.page = page;
        this.size = size;
        this.sort = sort;
    }
    // 省略上面三个属性的get方法
}

public class PageBounds extends RowBounds implements Serializable {
     public PageBounds(int page, int limit, List<PageOrder> orders, 
                       boolean containsTotalCount) {
        this.page = page;
        this.limit = limit;
        this.orders = orders;
        this.containsTotalCount = containsTotalCount;
    }
}
//PageOrder.formString=Order.formString(sortString)
//上面的PageOrder.formString方法代码就省略了, 主要是对参数”sort:字段名称(别名).
// 排序方式”的解析并重新组合

  

```

* 参考博客
```
PageHelper相对mybatis-paginator更加强大,主要体现在PageInfo信息的封装,更加适用于前端页面
http://blog.csdn.net/xubingchuan_blog/article/details/50933942
``` 






