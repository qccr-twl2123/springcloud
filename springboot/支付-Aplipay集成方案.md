### 支付宝集成解决方案

* 加载本地jar(alipay-sdk-java20170411150054.jar)
```xml
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

<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>alipay-sdk-java</artifactId>
    <version>java20170411150054</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/libs/alipay-sdk-java20170411150054.jar</systemPath>
</dependency>
```
* application.ymi配置
```yaml
pay:
  alipay:
     appId: 2017071907811942
     privateKey: xxxxx 
     alipayPulicKey: xxxx
     serverUrl: https://openapi.alipay.com/gateway.do
     notifyDomain: http://www.livtrip.com/alipay/notify.do
```
* 支付宝RSA公私钥 和 应用公钥(alipayPulicKey)配置
```
1.公钥私钥按照下面博文配置,取到私钥配置到privateKey
http://www.jianshu.com/p/2bb7e8a719ee
2.应用公钥需要在支付宝后台获取(alipayPulicKey),如下图

```　
![输入图片说明](https://github.com/qccr-twl2123/springcloud/blob/master/images/支付宝alipayPulicKey.png "在这里输入图片标题")



