/**
 * @(#)Properties.java, 2018-08-20.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmqclient;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * LightMQProperties
 *
 * @author lirongqian
 * @since 2018/08/20
 */
@ConfigurationProperties(prefix = "com.stalary.lightmq")
@Data
public class LightMQProperties {

    /** 服务端地址 **/
    private String url = "http://120.24.5.178:8001";

    /** 消费者组，默认master **/
    private String group = "";

    /** 是否开启消费者，默认开启 **/
    private boolean consumer = true;

    /** 生产模式，默认非顺序 **/
    private boolean order = false;

    /** 消费模式，默认非阻塞 **/
    private boolean block = false;
}