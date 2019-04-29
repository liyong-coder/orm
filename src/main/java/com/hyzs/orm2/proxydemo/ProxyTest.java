package com.hyzs.orm2.proxydemo;

import java.lang.reflect.Proxy;

public class ProxyTest {


    public static void main(String[] args) {
        TestableImpl testableImpl=new TestableImpl();
        TestableInvocationHandler testableInvocationHandler=new TestableInvocationHandler(testableImpl);
        Testable testable=(Testable) Proxy.newProxyInstance(ProxyTest.class.getClassLoader(), new Class<?>[]{Testable.class}, testableInvocationHandler);
        testable.test();
    }
}
