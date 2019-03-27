### SPU or SKU 
```text
•SPU = Standard Product Unit
(标准化产品单元)SPU是商品信息聚合的最小单位，是一组可复用标准化信息的集合
•SKU=stockkeeping unit
(库存量单位)SKU即库存进出计量的单位，可以是以件、盒、托盘等为单位

spu 指的是商品（iphone6s），spu属性就是不会影响到库存和价格的属性, 又叫**关键属性**
与商品是一对一的关系，比如

毛重: 420.00 g
产地: 中国大陆

sku指的是具体规格单品（玫瑰金 16G），sku属性就是会影响到库存和价格的属性, 又叫**销售属性**
与商品是多对一的关系，比如

容量: 16G, 64G, 128G
颜色: 银, 白, 玫瑰金

所以iphone6s则会生成 3 * 3 = 9 个 sku
```

* 电商创建商品流程
```text
1. 商家创建类目
2. 商家创建商品，并选择类目
3. 录入属性相关信息.
    生成spu(品牌_产地_材质_颜色_重量) 判断SPU是否存在，不存在则添加
    生成sku(iphone6_china_金属_黑色_1400), 判断SKU是否存在, 不存在则添加
    生成库存stock(goods_id,sku_id,number)
    
从数据库的角度看:
1. 一个spu 会生成多个sku
2. 一个spu 会对应多个商品数据    
3. 一个sku 会对应生成一条stock库存数据   
```

![输入图片说明](https://github.com/qccr-twl2123/springcloud/blob/master/images/电商属性.png "在这里输入图片标题")


* 数据库ER图
![输入图片说明](https://github.com/qccr-twl2123/springcloud/blob/master/images/spu-sku.png "在这里输入图片标题")


* 参考链接
[ecshop数据库设计](http://book.ecmoban.com/images/db.htm)
[SKU SPU 设计](https://blog.csdn.net/sinat_41832255/article/details/80886494)
[SKU SPU 数据表结构设计](https://blog.csdn.net/weixin_42323802/article/details/84976975)
