package com.hyzs.orm2.proxydemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TestableInvocationHandler implements InvocationHandler {


    private Object target;


    public TestableInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //Object的一些方法
        // toString()
        System.out.println("TestableInvocationHandler...before...");
        Object returnValue=method.invoke(this.target,args);
        System.out.println("TestableInvocationHandler...after...");
        return returnValue;
    }
}
