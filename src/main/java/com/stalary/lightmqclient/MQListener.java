package com.stalary.lightmqclient;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Stalary
 * @description
 * @date 2018/7/3
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MQListener {

    /** 监听的topics **/
    String[] topics() default {};
}
