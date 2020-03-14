### spring mvc 配置和初始化

####
```text
1. spring mvc 模块如何加载到spring容器？
2. spring @controller 如何扫描到map集合？
3. dispatcherServlet 如何在如何handlerMapping集合？
4. WebApplicationInitializer扩展mvc具体的应用场景有哪些(类和方法)？
5. WebMvcConfigure 接口是在哪个节点发挥作用？
```

#### 反射的运用
```java
//通过反射将class初始化成对象
initializers.add((WebApplicationInitializer)ReflectionUtils.accessibleConstructor(waiClass, new Class[0]).newInstance());

```

#### 配置
```text


```

#### 初始化
```text
如果你的项目某些类或方法,需要在启动的时候被Tomcat调用的话
```

- [x] 扫描配置配件
```text
spring-web项目下找到: META/services/javax.servlet.ServletContainerInitializer
```

- [x] 扫描配置配件
```java
@HandlesTypes({WebApplicationInitializer.class}) 
// HandlesTypes注解实现扫描所有实现WebApplicationInitializer的类，并封装Set<Class<?>> webAppInitializerClasses
public class SpringServletContainerInitializer implements ServletContainerInitializer {
    public SpringServletContainerInitializer() {
    }

    public void onStartup(@Nullable Set<Class<?>> webAppInitializerClasses, ServletContext servletContext) throws ServletException {
        //将class初始化成对象
        List<WebApplicationInitializer> initializers = new LinkedList();
        Iterator var4;
        if (webAppInitializerClasses != null) {
            var4 = webAppInitializerClasses.iterator();

            while(var4.hasNext()) {
                Class<?> waiClass = (Class)var4.next();
                if (!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers()) && WebApplicationInitializer.class.isAssignableFrom(waiClass)) {
                    try {
                        //通过反射将class初始化成对象
                        initializers.add((WebApplicationInitializer)ReflectionUtils.accessibleConstructor(waiClass, new Class[0]).newInstance());
                    } catch (Throwable var7) {
                        throw new ServletException("Failed to instantiate WebApplicationInitializer class", var7);
                    }
                }
            }
        }

        if (initializers.isEmpty()) {
            servletContext.log("No Spring WebApplicationInitializer types detected on classpath");
        } else {
            servletContext.log(initializers.size() + " Spring WebApplicationInitializers detected on classpath");
            AnnotationAwareOrderComparator.sort(initializers);
            var4 = initializers.iterator();

            while(var4.hasNext()) {
                //循环遍历去调用你项目中的实现
                WebApplicationInitializer initializer = (WebApplicationInitializer)var4.next();
                initializer.onStartup(servletContext);
            }

        }
    }
}
```
- [x] 请求分发
```text
1. spring 扫描整个项目
2. 拿到所有加了controller注解的类
3. 遍历类中所有的方法对象
4. 判断@ResuqetMapping
5. 将@ResuqetMapping中注解作为map的key，method作为 value 放入map
6. 根据用户请求从map中寻找执行对象

HandlerExecutionChain 处理程序执行链

processed Request 已处理请求

```

````java
class Dispatcher{
   
    //doGet,doPost->doService->doDispatch(核心)
    /**
    * 1. 请求信息封装文件相关数据
    * 2. 根据请求信息查询HandlerMap
    * 3. 根据Handler查找处理的适配器(controller=实现接口+@Controller)
    */
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpServletRequest processedRequest = request;
        HandlerExecutionChain mappedHandler = null;
        boolean multipartRequestParsed = false;
        WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
    
        try {
            try {
                ModelAndView mv = null;
                Object dispatchException = null;
    
                try {
                    //检查文件
                    processedRequest = this.checkMultipart(request);
                    multipartRequestParsed = processedRequest != request;
                    //
                    mappedHandler = this.getHandler(processedRequest);
                    if (mappedHandler == null) {
                        this.noHandlerFound(processedRequest, response);
                        return;
                    }
    
                    HandlerAdapter ha = this.getHandlerAdapter(mappedHandler.getHandler());
                    String method = request.getMethod();
                    boolean isGet = "GET".equals(method);
                    if (isGet || "HEAD".equals(method)) {
                        long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
                        if ((new ServletWebRequest(request, response)).checkNotModified(lastModified) && isGet) {
                            return;
                        }
                    }
    
                    if (!mappedHandler.applyPreHandle(processedRequest, response)) {
                        return;
                    }
    
                    mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
                    if (asyncManager.isConcurrentHandlingStarted()) {
                        return;
                    }
    
                    this.applyDefaultViewName(processedRequest, mv);
                    mappedHandler.applyPostHandle(processedRequest, response, mv);
                } catch (Exception var20) {
                    dispatchException = var20;
                } catch (Throwable var21) {
                    dispatchException = new NestedServletException("Handler dispatch failed", var21);
                }
    
                this.processDispatchResult(processedRequest, response, mappedHandler, mv, (Exception)dispatchException);
            } catch (Exception var22) {
                this.triggerAfterCompletion(processedRequest, response, mappedHandler, var22);
            } catch (Throwable var23) {
                this.triggerAfterCompletion(processedRequest, response, mappedHandler, new NestedServletException("Handler processing failed", var23));
            }
    
        } finally {
            if (asyncManager.isConcurrentHandlingStarted()) {
                if (mappedHandler != null) {
                    mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
                }
            } else if (multipartRequestParsed) {
                this.cleanupMultipart(processedRequest);
            }
    
        }
    }
}


````

- [x] 扫描配置配件
