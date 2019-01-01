package com.stalary.lightmqclient; /**
 * @(#)MessageDto.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */

import lombok.Data;

/**
 * MessageDto
 * 存储消息
 * @author lirongqian
 * @since 2018/06/18
 */
@Data
public class MessageDto {

    /**
     * 主题
     */
    private String topic;

    /**
     * 键，可以为空
     */
    private String key;

    /**
     * 值
     */
    private String value;

    private Long offset;

    public MessageDto(Long offset, String topic, String key, String value) {
        this.topic = topic;
        this.key = key;
        this.value = value;
        this.offset = offset;
    }
}