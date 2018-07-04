/**
 * @(#)Producer.java, 2018-07-03.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmqclient.facade;

import com.stalary.lightmqclient.JsonResponse;
import com.stalary.lightmqclient.WebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Producer
 *
 * @author lirongqian
 * @since 2018/07/03
 */
@Service
public class Producer {

    @Autowired
    private WebClientService service;

    public JsonResponse send(String topic, String key, String value) {
        return service.send(topic, key, value);
    }

    public JsonResponse send(String topic, String value) {
        return send(topic, "", value);
    }
}