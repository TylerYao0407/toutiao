package com.tyler.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by tyler on 2017/4/2.
 */
@Aspect
@Component
public class LogAspect {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Before("execution(* com.tyler.controller.IndexController.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        logger.info("方法执行之前：");
    }
    @After("execution(* com.tyler.controller.IndexController.*(..))")
    public void afterMethod(JoinPoint joinPoint){
        logger.info("方法执行之后：");

    }
}
