package com.stalary.lightmqclient;

import com.alibaba.fastjson.JSONObject;
import com.stalary.lightmqclient.facade.MQConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author stalary
 */
@Slf4j
public class ListenerLocator implements ApplicationContextAware {

    @Autowired
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
                    // 当开启消费者时才进行轮询
                    String[] topics = annotation.topics();
                    for (String topic : topics) {
                        executor.execute(() -> listener(topic, method, clazz));
                    }
                } else {
                    log.warn("annotation MQListener is not exist!");
                }
            } catch (Exception e) {
                log.warn("ListenerLocator error: " + e);
            }
        });
    }

    private void listener(String topic, Method method, Class<? extends MQConsumer> clazz) {
        for (;;) {
            JsonResponse jsonResponse = service.get(topic);
            // code为10代表消费者未启动
            if (jsonResponse != null && jsonResponse.get("code").equals(10)) {
                break;
            }
            if (jsonResponse != null && jsonResponse.get("data") != null) {
                Object data = jsonResponse.get("data");
                MessageDto messageDto = JSONObject.parseObject(JSONObject.toJSONString(data), MessageDto.class);
                try {
                    method.invoke(clazz.newInstance(), messageDto);
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    log.warn("listener error", e);
                }
            } else {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    log.warn("listener error", e);
                }
            }
        }
    }

}