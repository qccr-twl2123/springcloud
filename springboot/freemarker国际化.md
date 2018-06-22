* [1.简介](#1)
* [2 配置](#2)
* [3 语言切换](#3)
    * [3.1 方案一](#3.1)
    * [3.2 方案二](#3.2)
* [4 注意事项](#4)
* [5 附录](#5)


<h2 id="1">1.简介</h2>
```text
springboot+freemarker 国际化解决方案
```

<h2 id="2">2.配置</h2>
* 配置application.yml文件
```text
spring:
    messages:
      basename: i18n/messages
```
* 创建资源,在src/main/resources文件夹下创建i18n文件夹，并新建三个文件:
```text

message.properties(默认语言包)
message_zh_CN.properties（中文语言包）
message_en_US.properties（英文语言包）
将org.springframework.web.servlet.view.freemarker.spring.ftl复制到/src/main/resources/templates下
```
* 配置application.yml文件
```text

spring:
  freemarker:
    settings:
      auto_import: /spring.ftl as spring
```  
* 初始化LocaleResolver
```java

@Configuration
  public class WebConfig extends WebMvcConfigurerAdapter {
      @Bean
      public LocaleResolver localeResolver() {
          CookieLocaleResolver cl = new CookieLocaleResolver();
          cl.setCookieName("language");//根据浏览器的语言决定程序使用的语言
          return cl;
      }
}
```
* 使用@spring message 标签
```text

  message_zh_CN.properties
  bjdd=BJDD
  message_en_US.properties
  bjdd=比较叼的
  demo.html
  <h1><@spring.message "bjdd" /></h1>
```
<h2 id="3">1.语言切换</h2>
<h2 id="3.1">3.1 方案一(控制器方法)</h2>
```java

@GetMapping("changeLanguage")
    public String changeLanguage(String lang, HttpSession session, HttpServletResponse response) {
        if ("zh".equals(lang)) {
            session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("zh", "CN"));
        } else if ("en".equals(lang)) {
            session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("en", "US"));
        }
        return "ok";
    }
```
<h2 id="3.2">3.2.方案二(配置法)</h2>
```java
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application extends WebMvcConfigurerAdapter {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
		return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
}
```
* 在页面上切换操作
```html
 <a href="?lang=en_US" > 英语</a>
 <a href="?lang=zh_CN" > 中文</a>
```

<h2 id="4">4.注意事项</h2>
```text
中文编辑
正确
welcome = \u6B22\u8FCE\u6765\u5230Spring\u56FD\u9645\u5316\u9875\u9762
submit = \u63D0\u4EA4

错误
welcome=欢迎
```

<h2 id="5">5.附录</h2>
```text
spring.ftl 链接
https://github.com/spring-projects/spring-framework/edit/master/spring-webmvc/src/main/resources/org/springframework/web/servlet/view/freemarker/spring.ftl
```



