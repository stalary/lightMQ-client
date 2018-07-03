package com.stalary.lightmqclient;

import com.stalary.lightmqclient.facade.MQConsumer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * @author stalary
 */
@Component
public class ServiceLocator implements ApplicationContextAware{

    @Resource
    private WebClientService service;

    /**
     * 获取应用上下文并获取相应的接口实现类
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        //根据接口类型返回相应的所有bean
        Map<String, MQConsumer> map = applicationContext.getBeansOfType(MQConsumer.class);
        System.out.println(map);
        map.forEach((k, v) -> {
            Class<? extends MQConsumer> clazz = v.getClass();
            try {
                Method process = clazz.getDeclaredMethod("process");
                MQListener annotation = process.getAnnotation(MQListener.class);
                if (annotation != null) {
                    System.out.println(Arrays.toString(annotation.topics()));
                    System.out.println(service.get("", "test"));
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

}