/**
 * @(#)Properties.java, 2018-08-20.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmqclient;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * LightMQProperties
 *
 * @author lirongqian
 * @since 2018/08/20
 */
@ConfigurationProperties(prefix = "com.stalary.lightmq")
public class LightMQProperties {

    private String url = "http://120.24.5.178:8001";

    private String group = "";

    public String getUrl() {
        return url;
    }

    public String getGroup() {
        return group;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}