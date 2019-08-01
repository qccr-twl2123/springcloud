package cn.com.duiba.tuia.activity.center.biz.factory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br>
 * Description: 服务索引目录
 *
 * @author xierongli
 * @create 2019-07-29 14:21
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext = null;

    private static final Map<Class, Object> dataTableMap = new ConcurrentHashMap<>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    public static <T> T of(Class<T> clazz){
        T instance = (T)dataTableMap.get(clazz);
        if(null != instance){
            return instance;
        }
        dataTableMap.put(clazz, getBean(clazz));
        return (T)dataTableMap.get(clazz);
    }

    @SuppressWarnings("all")
    private static <T>  T getBean(String beanName){
        return (T) applicationContext.getBean(beanName);
    }

    @SuppressWarnings("all")
    private static <T>  T getBean(Class beanName){
        return (T) applicationContext.getBean(beanName);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
