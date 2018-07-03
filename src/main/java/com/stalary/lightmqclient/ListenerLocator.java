package com.stalary.lightmqclient;

import com.alibaba.fastjson.JSONObject;
import com.stalary.lightmqclient.facade.MQConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author stalary
 */
@Component
@Slf4j
public class ListenerLocator implements ApplicationContextAware {

    @Resource
    private WebClientService service;

    private ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * 获取应用上下文并获取相应的接口实现类
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        //根据接口类型返回相应的所有bean
        Map<String, MQConsumer> map = applicationContext.getBeansOfType(MQConsumer.class);
        map.forEach((k, v) -> {
            Class<? extends MQConsumer> clazz = v.getClass();
            try {
                Method method = clazz.getMethod("process", MessageDto.class);
                MQListener annotation = method.getAnnotation(MQListener.class);
                if (annotation != null) {
                    String[] topics = annotation.topics();
                    executor.submit(() -> {
                        listener(topics, method, clazz);
                    });
                }
            } catch (Exception e) {
                log.warn("ListenerLocator error: " + e);
            }
        });
    }

    public String listener(String[] topics, Method method, Class<? extends MQConsumer> clazz) {
        for (String topic : topics) {
            JsonResponse jsonResponse = service.get(topic);
            if (jsonResponse != null && jsonResponse.get("data") != null) {
                Object data = jsonResponse.get("data");
                MessageDto messageDto = JSONObject.parseObject(JSONObject.toJSONString(data), MessageDto.class);
                try {
                    method.invoke(clazz.newInstance(), messageDto);
                    TimeUnit.SECONDS.sleep(1);
                    return listener(topics, method, clazz);
                } catch (InterruptedException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    log.warn("listener error", e);
                    return null;
                }
            } else {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    return listener(topics, method, clazz);
                } catch (InterruptedException e) {
                    log.warn("listener error", e);
                    return null;
                }
            }
        }
        return null;
    }

}