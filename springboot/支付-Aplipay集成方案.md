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

### SpringBoot 开箱即用解决方案
*  maven依赖
```text

<dependency>
    <groupId>net.guerlab</groupId>
    <artifactId>sdk-alipay-starter</artifactId>
    <version>1.0.3</version>
</dependency>


```
* 配置文件
```yaml
sdk:
  alipay:
    dev: true/false #默认false,为true表示使用沙箱环境
    sign-type: RSA2 #签名算法
    app-id: #应用ID
    private-key: #应用私钥
    alipay-public-key: #支付宝公钥

```
* 增加控制器实现
```java
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;

import net.guerlab.sdk.alipay.controller.AlipayAbstractController;


@RequestMapping("/pay/alipay")
public class AlipayController extends AlipayAbstractController {

    @Autowired
    private AlipayClient client;//支付宝请求sdk客户端

    /**
     * 支付请求
     */
    @GetMapping("/app/{orderId}")
    public String app(
            @PathVariable Long orderId,
            HttpServletResponse httpResponse) {
        
        JSONObject data = new JSONObject();
        data.put("out_trade_no", "201701010000001234"); //商户订单号
        data.put("product_code", "QUICK_MSECURITY_PAY"); //产品码, APP支付 QUICK_MSECURITY_PAY, PC支付 FAST_INSTANT_TRADE_PAY, 移动H5支付 QUICK_WAP_PAY
        data.put("total_amount", "0.01"); //订单金额
        data.put("subject", "测试订单"); //订单标题

        //APP支付
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //PC支付
        //AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //移动H5支付
        //AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl("http://demo/pay/alipay/notify/1"); //异步通知地址
        request.setBizContent(data.toJSONString()); //业务参数

        return client.sdkExecute(request).getBody();
    }

    @PostMapping("/notify/{orderId}")
    public String notify(
            @PathVariable Long orderId,
            HttpServletRequest request) {
        if (!notify0(request.getParameterMap())) {
            //这里处理验签失败
        }

        request.getParameter("trade_no");//获取请求参数中的商户订单号

        return "success";
    }
}
```




* 参考博客
```text
支付宝解决方案
https://gitee.com/xierongli20162017/IJPay

springboot 开箱即用方案-支付宝
https://gitee.com/xierongli20162017/sdk-alipay

```




