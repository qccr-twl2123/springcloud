### springboot 加载jar包中的文件

```java
StringBuffer stringBuffer = new StringBuffer();
InputStream stream = getClass().getClassLoader().getResourceAsStream("json/slot_baidu.json");
BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
String line;
while ((line = br.readLine()) != null) {
    stringBuffer.append(line);
}

```