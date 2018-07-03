/**
 * @(#)MQConsumer.java, 2018-07-03.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmqclient.facade;

/**
 * MQConsumer
 * 实现该接口实现消费
 * @author lirongqian
 * @since 2018/07/03
 */
public interface MQConsumer {

    void process();
}