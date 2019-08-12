### JAVA 反射相关知识点

* 获取注解(AnnotationUtils.getAnnotation)
```java
PropertySourcedMapping mapper = (PropertySourcedMapping)AnnotationUtils.getAnnotation(method, PropertySourcedMapping.class);
```
* 获取Class对象的三种方法
```text
(1)知道类的全路径名：Class<?> clazz = Class.forName("类的全路径名");
(2)知道类的名字：  Class<?> clazz = 类名.class;
(3)知道该类的一个对象 ：  Class<?> clazz = 对象名.getClass();
```

* 获取类声明的方法
```java
 Method[] methods = clazz.getDeclaredMethods();
```

* 获取类声明的属性
```java
Field[] fields = clazz.getDeclaredFields();
```

* 获取类声明的构造器
```java
Constructor<?>[] constructors = clazz.getDeclaredConstructors();
```
