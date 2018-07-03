/**
 * @(#)MQConsumer.java, 2018-07-03.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmqclient.facade;

import com.stalary.lightmqclient.MessageDto;

/**
 * MQConsumer
 * 实现该接口实现消费
 * @author lirongqian
 * @since 2018/07/03
 */
public interface MQConsumer {

    void process(MessageDto messageDto);
}