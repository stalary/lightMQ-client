/**
 * @(#)TestController.java, 2018-07-03.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmqclient;

import com.stalary.lightmqclient.facade.Consumer;
import com.stalary.lightmqclient.facade.Producer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TestController
 *
 * @author lirongqian
 * @since 2018/07/03
 */
@RestController
public class TestController {

    @Resource
    private WebClientService webClientService;

    @Resource
    private Consumer consumer;

    @Resource
    private Producer producer;

    @GetMapping("/test")
    public JsonResponse test() {
        return JsonResponse.success(webClientService.getAll());
    }

    @GetMapping("/send")
    public JsonResponse send(
            @RequestParam String topic,
            @RequestParam(required = false, defaultValue = "") String key,
            @RequestParam String value) {
        return producer.send(topic, key, value);
    }

    @GetMapping("/get")
    public JsonResponse consume(
            @RequestParam(required = false, defaultValue = "") String group,
            @RequestParam String topic) {
        return JsonResponse.success();
    }

    public static void main(String[] args) {

    }

}