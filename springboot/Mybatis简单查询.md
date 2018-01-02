### Mybatis  用法总结

#### 新增相关
* insert 之后返回自增主键
```xml
<insert id="insertAndGetId" useGeneratedKeys="true" keyProperty="userId" parameterType="com.chenzhou.mybatis.User">
    insert into user(userName,password,comment)
    values(#{userName},#{password},#{comment})
</insert>
```
```text
User user = new User();
user.setUserName("chenzhou");
user.setPassword("xxxx");
user.setComment("测试插入数据返回主键功能");

System.out.println("插入前主键为："+user.getUserId());
userDao.insertAndGetId(user);//插入操作
System.out.println("插入后主键为："+user.getUserId()); 
```

* 批量插入
```xml
<insert id= "batchAddPromotionStatistic" parameterType= "java.util.List" >
    insert into b_promotion_statistic( <include refid="ALL_Columns_Insert" />)
    values
     <foreach collection="list" item="item" index="index" separator="," >
          (#{item.promotionId}, #{item.source}, now(), #{item.number})
     </foreach>
</insert>
<!--ALL_Columns_Insert: 表示要插入的字段.-->
```

#### 更新相关

* 批量更新
```xml
<update id="updateBatch" parameterType="Map">
    update aa set
    a=#{fptm},
    b=#{csoftrain}
    where c in
    <foreach collection="cs" index="index" item="item" pen="("separator=","close=")">
           #{item}
    </foreach>
</update>
<!--更新的是相同的字段.-->
<update id="batchUpdate" parameterType="java.util.List">
     <foreach collection="list" item="item" index="index" open="" close="" separator=";">
update test
<set>
test=${item.test}+1
</set>
where id = ${item.id}
</foreach>
</update>
```

#### 查询相关
* mybatis 查询in 语句的使用.
```text
1. 当查询的参数只有一个时 
  findByIds(List<Long> ids)
1.1 如果参数的类型是List, 则在使用时，collection属性要必须指定为 list
<select id="findByIdsMap" resultMap="BaseResultMap">
 Select
      <include refid="Base_Column_List" />
 from jria where ID in
     <foreach item="item" index="index" collection="list" open="(" separator="," close=")">
                 #{item}
     </foreach>
</select>

   findByIds(Long[] ids)
1.2 如果参数的类型是Array,则在使用时，collection属性要必须指定为 array
 <select id="findByIdsMap" resultMap="BaseResultMap">
 select
     <include refid="Base_Column_List" />
 from tabs where ID in
     <foreach item="item" index="index" collection="array" open="(" separator="," close=")">
      #{item}
     </foreach>
 </select>
```



*  当查询的参数有多个时,例如 findByIds(String name, Long[] ids)
```text
 这种情况需要特别注意，在传参数时，一定要改用Map方式, 这样在collection属性可以指定名称
         下面是一个示例
         Map<String, Object> params = new HashMap<String, Object>(2);
         params.put("name", name);
         params.put("ids", ids);
        mapper.findByIdsMap(params);

<select id="findByIdsMap" resultMap="BaseResultMap">
 select
     <include refid="Base_Column_List" />
 from tabs where ID in
     <foreach item="item" index="index" collection="ids" open="(" separator="," close=")">
         #{item}
     </foreach>
</select>

```
* 模糊查询.
```mysql
select * from user where username LIKE  cancat('%',#{username}),'%')
```



