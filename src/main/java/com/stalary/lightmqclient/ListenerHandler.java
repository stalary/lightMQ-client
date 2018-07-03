/**
 * @(#)ListenerHandler.java, 2018-07-03.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmqclient;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * ListenerHandler
 *
 * @author lirongqian
 * @since 2018/07/03
 */
@Aspect
@Component
public class ListenerHandler {

    @Pointcut("@annotation(MQListener)")
    public void annotationPointCut() {}

    // 声明一个advice,并使用@pointCut定义的切点
    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //从切面中获取当前方法
        Method method = signature.getMethod();
        //得到了方,提取出他的注解
        MQListener listener = method.getAnnotation(MQListener.class);
        System.out.println(Arrays.toString(listener.topics()));
        //输出
    }

    //定义方法拦截的规则
    @Before("execution(* process(..))")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature =  (MethodSignature) joinPoint.getSignature();
        //拦截了方法
        Method method = signature.getMethod();
        //直接获取方法名字
        System.out.println("方法规则式拦截" + method.getName());
    }
}