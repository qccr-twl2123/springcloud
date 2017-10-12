### 支付宝集成解决方案

#### 支付流程

![输入图片说明](https://github.com/qccr-twl2123/springcloud/blob/master/images/支付流程.png "在这里输入图片标题")

#### 退款流程

![输入图片说明](https://github.com/qccr-twl2123/springcloud/blob/master/images/退款流程.png "在这里输入图片标题")

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
```text
1.公钥私钥按照下面博文配置,取到私钥配置到privateKey
http://www.jianshu.com/p/2bb7e8a719ee
2.应用公钥需要在支付宝后台获取(alipayPulicKey),如下图

```

![输入图片说明](https://github.com/qccr-twl2123/springcloud/blob/master/images/支付宝alipayPulicKey.png "在这里输入图片标题")


* 简化版代码实现
```java

public interface PayService {
    void pcPay(HttpServletResponse response,AlipayTradePayModel model,String returnUrl,String notifyUrl);
    String refund(AlipayTradeRefundModel alipayTradeRefundModel);
}

@Service
public class PayServiceImpl implements PayService {
    public final static Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    private  String charset = "UTF-8";
    @Value("${pay.alipay.privateKey}")
    private  String privateKey;
    @Value("${pay.alipay.alipayPulicKey}")
    private  String alipayPublicKey;
    @Value("${pay.alipay.serverUrl}")
    private  String serviceUrl;

    @Value("${pay.alipay.appId}")
    private  String appId ;
    private  String signType = "RSA2";
    @Value("${pay.alipay.notifyDomain}")
    private  String notifyDomain;

    public AliPayApiConfig getApiConfig() {
        AliPayApiConfig aliPayApiConfig = AliPayApiConfig.New()
                .setAppId(appId)
                .setAlipayPublicKey(alipayPublicKey)
                .setCharset(charset)
                .setPrivateKey(privateKey)
                .setServiceUrl(serviceUrl)
                .setSignType(signType)
                .build();
        return aliPayApiConfig;
    }


    @Override
    public void pcPay(HttpServletResponse response, AlipayTradePayModel model, String returnUrl, String notifyUrl) {
        try {
            AliPayApi.tradePage(getApiConfig(),response, model , notifyUrl, returnUrl);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public String refund(AlipayTradeRefundModel alipayTradeRefundModel) {
        try {
           return  AliPayApi.tradeRefund(getApiConfig(),alipayTradeRefundModel);
        } catch (AlipayApiException e) {
            logger.error("退款失败:"+e.getMessage());
            e.printStackTrace();
            return "";
        }
    }
}
```

* 参考博客
```text
https://gitee.com/xierongli20162017/IJPay
```




