### 定时任务

* @Scheduled 实现定时任务
```java
@Component
public class ScheduledTasks {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
  @Scheduled(fixedRate = 5000)
  public void reportCurrentTime() {
      System.out.println("现在时间：" + dateFormat.format(new Date()));
  }
}

```

```
@Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行
@Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
@Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
@Scheduled(cron="*/5 * * * * *") ：通过cron表达式定义规则

```

*Quartz定时任务

```
Quartz设计者做了一个设计选择来从调度分离开作业。Quartz中的触发器用来告诉调度程序作业什么时候触发。
```
