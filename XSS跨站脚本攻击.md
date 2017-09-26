### XSS 跨站脚本攻击
#### 前言
```
跨站脚本攻击(Cross Site Scripting)，为不和层叠样式表(Cascading Style Sheets, CSS)的缩写混淆，
故将跨站脚本攻击缩写为XSS。恶意攻击者往Web页面里插入恶意Script代码，
当用户浏览该页之时，嵌入其中Web里面的Script代码会被执行，从而达到恶意攻击用户的目的。
```
#### 思路
```
基于filter拦截,将特殊字符替换为html转意字符 (如: "<" 转意为 "<") , 需要拦截的点如下:
请求头 requestHeader
请求体 requestBody
请求参数 requestParameter
```

#### 代码实现
```
1.Config配置文件
@Configuration
public class WebConfig {
   /**
     * xssFilter注册
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new XssFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

}
    
2.XSSFilter
    public class XssFilter implements Filter {
    
        FilterConfig filterConfig = null;
    
        public void init(FilterConfig filterConfig) throws ServletException {
            this.filterConfig = filterConfig;
        }
    
        public void destroy() {
            this.filterConfig = null;
        }
    
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            chain.doFilter(new XssHttpServletRequestWrapper(
                    (HttpServletRequest) request), response);
        }
    
    }

3.XssHttpServletRequestWrapper
    
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;

    }

    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);

    }

    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
            return null;
        return cleanXSS(value);
    }

    private String cleanXSS(String value) {
        //You'll need to remove the spaces from the html entities below
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }
}




```
