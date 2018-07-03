/**
 * @(#)Consumer.java, 2018-07-03.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmqclient.facade;

import com.stalary.lightmqclient.MQListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Consumer
 *
 * @author lirongqian
 * @since 2018/07/03
 */
@Service
@Slf4j
public class Consumer implements MQConsumer {

    @Override
    @MQListener(topics = {"123", "456"})
    public void process() {
        System.out.println("123");
    }



    /*@Resource
    private WebClientService service;

    *//**
     * 消费消息
     * @param group
     * @param topic
     * @return
     *//*
    public String get(String group, String topic) {
        JsonResponse jsonResponse = service.get(group, topic);
        if (null == jsonResponse || null == jsonResponse.get("data")) {
            try {
                // 每一分钟轮询一次
                TimeUnit.MINUTES.sleep(1);
                return get(group, topic);
            } catch (InterruptedException e) {
                log.warn("get error ", e);
                return get(group, topic);
            }
        } else {
            return jsonResponse.get("data").toString();
        }
    }

    public String get(String topic) {
        return get("", topic);
    }*/
}