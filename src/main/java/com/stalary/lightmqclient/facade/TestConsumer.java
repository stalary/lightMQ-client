/**
 * @(#)TestConsumer.java, 2018-07-03.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmqclient.facade;

import org.springframework.stereotype.Service;

/**
 * TestConsumer
 *
 * @author lirongqian
 * @since 2018/07/03
 */
@Service
public class TestConsumer implements MQConsumer {
    @Override
    public void process() {
        System.out.println("test");
    }
}