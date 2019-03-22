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

* 数据库ER图
![输入图片说明](https://github.com/qccr-twl2123/springcloud/blob/master/images/spu-sku.png "在这里输入图片标题")


* 参考链接
[ecshop数据库设计](http://book.ecmoban.com/images/db.htm)
[SKU SPU 设计](https://blog.csdn.net/sinat_41832255/article/details/80886494)
