/**
 * @(#)Consumer.java, 2018-07-03.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmqclient.facade;

import com.stalary.lightmqclient.MQListener;
import com.stalary.lightmqclient.MessageDto;
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
    @MQListener(topics = {"test"})
    public void process(MessageDto messageDto) {
        System.out.println("receive message: " + messageDto);
    }

}