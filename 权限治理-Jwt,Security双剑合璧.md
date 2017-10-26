## JWT权限校验


### 经典的微服务权限验证解决方案
1.单点登录（SSO）方案
```
采用单点登录方案，意味着每个面向用户的服务都必须与认证服务交互，
这会产生大量非常琐碎的网络流量，同时这个防范实现起来也相当的复杂。
在其他方面，选择SSO方案安全性会很好，

用户登录状态是不透明的，可防止攻击者从状态中推断任何有用的信息。
```
2.分布式会话方案(redis存储session)
```
优点:
原理主要是将关于用户信息存储在共享内存中，并通常由用户会话作为key来实现简单的分布式哈希映射。
当用户访问微服务时，用户数据可以从共享存储中获取。
该方案的另外一个优点就是: 用户登录状态不是透明的。

缺点:高度依赖第三方redis的稳定性
```

3.token客户端令牌方案
```
优点:
此令牌在客户端生成，由身份验证服务进行签名，并且必须包含足够的信息，以便可以在所有微服务中建立用户身份。
令牌会附加到每一个请求上，为微服务提供身份验证。

缺点:
这种解决方案安全性相对较好，但身份验证注销是一个大大的问题，
缓解这种情况的方法可以使用短期令牌access_token 和频繁检查认证服务器等。

```

4.客户端令牌与API网关结合(JWT)
```
优点:
这个方案意味着所有的请求都通过网关，从而有效地隐藏了微服务。
在请求时，网关将原始用户令牌转换为内部会话（session）ID令牌。
在这种情况下，注销就不在是个大大的问题, 因为网关在注销时可以撤销用户的令牌

缺点:
实现相对复杂

```

#### 基于JWT 验证时序图
![输入图片说明](https://github.com/qccr-twl2123/livtrip/blob/master/src/main/resources/static/resources/images/JWT时序.png "在这里输入图片标题")


#### JWT认证原理简介
```
1.认证流程

客户端调用登录接口（或者获取 token 接口），传入用户名密码。
服务端请求身份认证中心，确认用户名密码正确。
服务端创建 JWT，返回给客户端。
客户端拿到 JWT，进行存储（可以存储在缓存中，也可以存储在数据库中，
如果是浏览器，可以存储在 Cookie 中）在后续请求中，在 HTTP 请求头中加上 JWT。
服务端校验 JWT，校验通过后，返回相关资源和数据。


2.JWT结构
JWT 是由三段信息构成的:
第一段为头部（Header），
第二段为载荷（Payload)，
第三段为签名（Signature）。

每一段内容都是一个 JSON 对象，将每一段 JSON 对象采用 BASE64 编码，将编码后的内容用.
链接一起就构成了 JWT 字符串

1.Header头部
头部用于描述关于该 JWT 的最基本的信息，例如其类型以及签名所用的算法等。
{
"typ": "JWT",
"alg": "HS256"
}


2.Payload载荷

载荷就是存放有效信息的地方。有效信息包含三个部分：
标准中注册的声明
公共的声明
私有的声明

标准中注册的声明（建议但不强制使用）：
iss：JWT 签发者
sub：JWT 所面向的用户
aud：接收 JWT 的一方
exp：JWT 的过期时间，这个过期时间必须要大于签发时间
nbf：定义在什么时间之前，该 JWT 都是不可用的
iat：JWT 的签发时间
jti：JWT 的唯一身份标识，主要用来作为一次性 token, 从而回避重放攻击。

示例代码:

{ "iss": "Online JWT Builder",
 "iat": 1416797419,
 "exp": 1448333419,
 "aud": "www.primeton.com",
 "sub": "devops@primeton.com",
 "GivenName": "dragon",
 "Surname": "wang",
 "admin": true
}

3.Signature签名
创建签名需要使用 Base64 编码后的 header 和 payload 以及一个秘钥。
将 base64 加密后的 header 和 base64 加密后的 payload 使用. 连接组成的字符串，
通过 header 中声明的加密方式进行加盐 secret 组合加密，然后就构成了 jwt 的第三部分。

比如：HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)

JWT 的优点：
跨语言，JSON 的格式保证了跨语言的支撑
基于 Token，无状态
占用字节小，便于传输


```
#### JWT实施步骤

```
Maven依赖:
<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt</artifactId>
	<version>0.6.0</version>
</dependency>


JWT的生成和解析

JWT的生成可以使用下面这样的代码完成：
String generateToken(Map<String, Object> claims) {
    return Jwts.builder()
            .setClaims(claims)
            .setExpiration(generateExpirationDate())
            .signWith(SignatureAlgorithm.HS512, secret) //采用什么算法是可以自己选择的，不一定非要采用HS512
            .compact();
}

数据声明（Claim）其实就是一个Map，
比如我们想放入用户名，可以简单的创建一个Map然后put进去就可以了。

Map<String, Object> claims = new HashMap<>();
claims.put(CLAIM_KEY_USERNAME, username());

解析也很简单，利用 jjwt 提供的parser传入秘钥，然后就可以解析token了。

Claims getClaimsFromToken(String token) {
    Claims claims;
    try {
        claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    } catch (Exception e) {
        claims = null;
    }
    return claims;
}

请求如何进行拦截(Filter核心代码):

HttpServletRequest httpRequest = (HttpServletRequest)request;  
String auth = httpRequest.getHeader("Authorization");  
if ((auth != null) && (auth.length() > 7))  
{  
    String HeadStr = auth.substring(0, 6).toLowerCase();  
    if (HeadStr.compareTo("bearer") == 0)  
    {  
          
        auth = auth.substring(7, auth.length());   
        if (JwtHelper.parseJWT(auth, audienceEntity.getBase64Secret()) != null)  
        {  
            chain.doFilter(request, response);  
            return;  
        }  
    }  
}  


```

#### JWT短板
```
1.在Web应用中，别再把JWT当做session使用，绝大多数情况下，
传统的cookie-session机制工作得更好

2. JWT适合一次性的命令认证，颁发一个有效期极短的JWT，
即使暴露了危险也很小，由于每次操作都会生成新的JWT，
因此也没必要保存JWT，真正实现无状态。

但我们的用户权限，对于API的权限划分、资源的权限划分，用户的验证等等都不是JWT负责的。
也就是说，请求验证后，你是否有权限看对应的内容是由你的用户角色决定的。
所以我们这里要利用Spring的一个子项目Spring Security来简化我们的工作。

```



参考博客:
```
JWT简介
http://www.infoq.com/cn/articles/identity-authentication-of-architecture-in-micro-service

JWT源码实现方案(推荐)
https://gitee.com/naan1993/guns

JWT,Security权限校验方案源码
https://github.com/qccr-twl2123/spring-boot-tut

http://www.jianshu.com/p/af8360b83a9f
```











