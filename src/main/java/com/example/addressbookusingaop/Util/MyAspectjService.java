package com.example.addressbookusingaop.Util;
import com.example.addressbookusingaop.Model.AddressBook;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.util.Arrays;
@Aspect
@Slf4j
@Component
public class MyAspectjService {
    @Before(value = "execution(* com.example.addressbookusingaop.Service.AddressBookService.*(..))")
    public void Before(JoinPoint joinPoint) {
        System.out.println("Before : " + joinPoint.getSignature().getName());
    }
    @Pointcut("execution(* com.example.addressbookusingaop.Service.AddressBookService.*(..))")
    public void loggingPointCut(){

    }
   /* @Before("loggingPointCut()")
    public void Before(JoinPoint joinPoint) {
        System.out.println("Before : " + joinPoint.getSignature().getName());
    }*/

    @After(value = "execution(* com.example.addressbookusingaop.Service.AddressBookService.*(..))")
    public void After(JoinPoint joinPoint) {
        System.out.println("After : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "execution(* com.example.addressbookusingaop.Service.AddressBookService.*(..))",returning = "addressBook")
    public void afterReturn(JoinPoint joinPoint, AddressBook addressBook){
        log.info("After :"+addressBook);
    }
}