package com.example.enotes.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.enotes.controller..*(..))")
    public void beforeController(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        log.info("Calling :: {} :: {}()", className, methodName);
    }

//    @After("execution(* com.example.enotes.controller..*(..))")
//    public void afterController(JoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        String className = signature.getDeclaringType().getSimpleName();
//        String methodName = signature.getName();
//
//        log.info("Calling :: {} :: {}()", className, methodName);
//    }

    @Around("execution(* com.example.enotes.controller..*(..))")
    public Object jointPointController(ProceedingJoinPoint joinPoint) throws Throwable{
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        log.info("Calling :: {} :: {}()", className, methodName);
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis() - startTime;
        log.info("Returning :: {} :: {}() :: for {}", className, methodName, endTime);

        return result;
    }

    @Around("execution(* com.example.enotes.service.impl..*(..))")
    public Object jointPointService(ProceedingJoinPoint joinPoint) throws Throwable{
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        log.info("Calling :: {} :: {}()", className, methodName);
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis() - startTime;
        log.info("Returning :: {} :: {}() :: for {}", className, methodName, endTime);

        return result;
    }

    @Around("execution(* com.example.enotes.service..*(..))")
    public Object jointPointServiceImpl(ProceedingJoinPoint joinPoint) throws Throwable{
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        log.info("Calling :: {} :: {}()", className, methodName);
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis() - startTime;
        log.info("Returning :: {} :: {}() :: for {}", className, methodName, endTime);

        return result;
    }
}
