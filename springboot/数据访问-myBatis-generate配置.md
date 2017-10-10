### Mybatis Generate 配置
```
Dao数据层操作一键生成,覆盖单表遇到的所有增删改查操作.
```
#### 配置步骤
* pom.xml 配置
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.1</version>
            <!-- compile for Java 1.8 -->
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
                <encoding>UTF-8</encoding>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-maven-plugin</artifactId>
            <version>1.3.5</version>
            <configuration>
                <verbose>true</verbose>
                <overwrite>true</overwrite>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>mysql</groupId>
                    <artifactId>mysql-connector-java</artifactId>
                    <version>${mysql.version}</version>
                </dependency>
                <!--这个jar包是生成Dao和Model代码核心-->
                <dependency>
                    <groupId>com.trj.mybatis.generator</groupId>
                    <artifactId>trj-mybatis-generator-plugin</artifactId>
                    <version>0.0.1-SNAPSHOT</version>
                </dependency>

            </dependencies>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-war-plugin</artifactId>
            <configuration>
                <webResources>
                    <resource>
                        <directory>${project.basedir}/libs</directory>
                        <targetPath>WEB-INF/lib</targetPath>
                        <filtering>false</filtering>
                        <includes>
                            <include>**/*.jar</include>
                        </includes>
                    </resource>
                </webResources>
            </configuration>
            <version>2.1.1</version>
        </plugin>

    </plugins>
</build>
	
<!--添加mysql依赖包 -->
<dependencies>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
    </dependency>
</dependencies>
```
* application.ymi配置文件
```yaml
app:
  datasource:
    livtrip:
      driverClassName: com.mysql.jdbc.Driver
      url: jdbc:mysql://192.168.10.80:3306/xxx?useSSL=false
      username: xxx
      password: xxx
      testOnBorrow: true
      testWhileIdle: true
      validationQuery: select 1
```
* 如何执行生成操作and效果图

![输入图片说明](https://github.com/qccr-twl2123/springcloud/blob/master/images/mybatis-generate生成操作.png "在这里输入图片标题")

* Dao常见的操作方法汇总
```java

 public interface BaseMapper<T, C, ID extends Serializable> {
     //根据条件统计数目
     long countByCriteria(C var1);
     //根据条件 删除
     int deleteByCriteria(C var1);
     //根据主键删除记录
     int deleteByPrimaryKey(ID var1);
     //插入记录,强一致性(字段若不为null,则插入之前必须补全数据)
     int insert(T var1);
     //插入记录,弱一致性(字段若不为null,则默认null)
     int insertSelective(T var1);
    
     List<T> selectByCriteriaWithRowbounds(C var1, RowBounds var2);
    
     List<T> selectByCriteria(C var1);
 
     T selectByPrimaryKey(ID var1);
 
     int updateByCriteriaSelective(@Param("record") T var1, @Param("example") C var2);
 
     int updateByCriteria(@Param("record") T var1, @Param("example") C var2);
 
     int updateByPrimaryKeySelective(T var1);
 
     int updateByPrimaryKey(T var1);
 }
 
 
//代码式例:
//简单查询
CityCriteria cityCriteria = new CityCriteria(); 
cityCriteria.createCriteria().andIsHotEqualTo(Byte.parseByte("1"));
//相当于:select * from city where is_hot = 1

//排序操作
CityCriteria cityCriteria = new CityCriteria(); 
cityCriteria.setOrderByClause("id ASC");
cityCriteria.createCriteria().andIsHotEqualTo(Byte.parseByte("1"));
//相当于:select * from city where is_hot = 1 order by id asc

//适用于后台查询功能
public PageInfo<Dest> pageQueryListByCondition(DestQuery destQuery) {
    PageHelper.startPage(destQuery.getPageNumber(),destQuery.getPageSize(),true);
    DestCriteria destCriteria = new DestCriteria();
    destCriteria.createCriteria();
    if(StringUtils.isNoneBlank(destQuery.getCityName())){
        destCriteria.getOredCriteria().get(0).andCityNameLike(destQuery.getCityName());
    }
    if(StringUtils.isNoneBlank(destQuery.getState())){
        destCriteria.getOredCriteria().get(0).andStateLike(destQuery.getState());
    }
    if(destQuery.getDestinationId() != null){
        destCriteria.getOredCriteria().get(0).andDestinationIdEqualTo(destQuery.getDestinationId());
    }
    List<Dest> dests = destMapper.selectByCriteria(destCriteria);
    return new PageInfo<Dest>(dests);
}

```


 