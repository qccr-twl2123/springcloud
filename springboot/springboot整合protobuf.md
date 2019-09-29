### springboot 整合protobuf

#### windows 安装proto环境
```text
1. 下载地址
https://github.com/protocolbuffers/protobuf/releases
https://github.com/protocolbuffers/protobuf/releases/download/v3.9.0/protoc-3.9.0-win64.zip，

2. 设置环境变量
在下载程序后，需要将下载 zip 文件中的 bin 目录设置到环境变量中。

然后运行 protoc --version 来确定你的编译运行版本已经被正确配置。

3. 通过运行 protoc --version 来确定已经配置正确和编译器版本。

4. protoc ./test.proto --java_out=./  (1.模版路径 2. 文件输出路径)
```


#### proto 文件定义说明
```text
message SearchRequest{
        required string query = 1;
        optional int32 page_number = 2;
        optional int32 result_per_page =3;
        repeated int32 samples = 4 [packed=true];    
}


1. 指定field类型

![输入图片说明](https://github.com/qccr-twl2123/springcloud/blob/master/images/proto-data-type.png "在这里输入图片标题")

2. 分配标签
每个field都是唯一数字的标记，
这是用来标记这个field在message二进制格式中的位置的，一旦使用就不能再修改顺序了。

3. 指定field规则

required
optional
repeated

```

* 有关enum message 特说说明
```text
在定义message类型的时候，也许会有这样一种需求：
其中的一个字段仅需要包含预定义的若干个值即可。
比如，对于每一个搜索请求，现需要增加一个分类字段，
分类包含：UNIVERSAL, WEB, IMAGES, LOCAL, NEWS, PRODUCTS or VIDEO。
要实现该功能，仅需要增加一个枚举类型字段。如下：


message SearchRequest {
    required string query = 1;
    optional int32 page_number = 2;
    optional int32 result_per_page = 3 [default = 10];
    enum Corpus {
       UNIVERSAL = 0;
       WEB = 1;
       IMAGES = 2;
       LOCAL = 3;
       NEWS = 4;
       PRODUCTS = 5;
       VIDEO = 6;
    }
    optional Corpus corpus = 4 [default = UNIVERSAL];
}
```
* 使用其他Message类型作为filed类型
```text
PB允许使用message类型作为filed类型。例如，在搜索相应message中，
包含一个结果message。此时，只需要定义一个结果message，
然后再.proto文件中，在搜索结果message中新增一个字段，该字段的类型设置为结果message即可。

message SearchResponse 
{
    repeated Result result = 1;
}

message Result 
{
    required string url = 1;
    optional string title = 2;
    repeated string snippets = 3;
}

在上例中，Result message类型与SearchResponse 定义在同一个文件中，假如有这么一种情况，这里所要使用的Resultmessage已经在其他的.proto文件中定义了呢？
可以通过导入其他.proto文件来使用其内的定义。为达此目的，需要在现.proto文件前增加一条import语句：

import "myproject/other_protos.proto";


protobuf建议字段的命名采用以下划线分割的驼峰式。例如 first_name 而不是firstName.
```


### springboot 集成proto 

* 使用protobuf工具生成sdk
```text
protoc --java_out=src/main/java src/main/resources/*.proto
```

* 服务提供方需要增加protobuf库的引用，以gradle为例子
```text
compile('com.google.protobuf:protobuf-java:3.6.1')
compile('com.googlecode.protobuf-java-format:protobuf-java-format:1.4')

```

* 服务提供方需要提供协议自动转换器，代码如下，直接使用即可：
```text
package com.hs.user.base.util;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
@Configuration
public class UserBaseServiceProtoConvertor {
   @Bean
    public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }
    @Bean
    public RestTemplate restTemplate(ProtobufHttpMessageConverter protobufHttpMessageConverter) {
        return new RestTemplate(Collections.singletonList(protobufHttpMessageConverter));
    }
}
```

* e.在服务提供方的Controller层，进行RequestMapping，代码如下，请注意@RequestMapping注解的使用，尤其是produces：

```java
package com.hs.user.base.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hs.user.base.entity.UserInfoEntity;
import com.hs.user.base.proto.UserBaseServiceProto.UserInfoRequest;
import com.hs.user.base.proto.UserBaseServiceProto.UserInfoResponse;
import com.hs.user.base.proto.UserBaseServiceProto.UserInfoResponse.Builder;
import com.hs.user.base.service.UserInfoService;

@Controller
public class UserInfoServiceController {
  private static Logger logger = LoggerFactory.getLogger(UserInfoServiceController.class);  
  @Autowired
  private UserInfoService userInfoService;
  
  @RequestMapping(value="/getUserInfo" , method=RequestMethod.POST , produces = "application/x-protobuf")
  public @ResponseBody UserInfoResponse getUserInfo(@RequestBody UserInfoRequest request) throws Exception {
    logger.info("请求：{}" , request.toString());
    
    UserInfoEntity user = userInfoService.getUserInfo(request.getUserId());
    
    Builder builder = UserInfoResponse.newBuilder();
    if (user != null) {
      builder.setMobile(user.getMobile());
      builder.setUserType(user.getUserType());
    }   
    return builder.build();
  }
}
```

* 服务调用方代码示例：
```java
package com.hs.user.base.test;


import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.hs.user.base.proto.UserBaseServiceProto.UserInfoRequest;
import com.hs.user.base.proto.UserBaseServiceProto.UserInfoResponse;

public class UserBaseServiceTest {
  public static void main(String [] args) throws IOException {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      URI uri = new URI("http", null, "127.0.0.1", 8080, "/getUserInfo", "", null);
      HttpPost post = new HttpPost(uri);
      UserInfoRequest.Builder builder = UserInfoRequest.newBuilder();
      builder.setUserId(178624L);     
            post.setEntity(new ByteArrayEntity(builder.build().toByteArray()));
            post.setHeader("Content-Type", "application/x-protobuf");
            
      HttpResponse response = httpClient.execute(post);
      
      if (response.getStatusLine().getStatusCode() == 200) {

        UserInfoResponse resp = UserInfoResponse.parseFrom(response.getEntity().getContent());
        
        System.out.println("result:" + resp.getMobile() + " " + resp.getUserType());
      } else {
        System.out.println(response.getStatusLine().getStatusCode());
      }
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      httpClient.close();
    }    
  }
}
```  
* 踩坑点???
```text
1. 用于生成java文件的proto版本与项目中proto的版本保持一致
```


* 参考文档
[springboot整合proto](https://cloud.tencent.com/developer/article/1504965)
[windows 配置proto环境](https://blog.51cto.com/ossez/2422995)