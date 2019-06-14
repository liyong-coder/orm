package com.hyzs.orm2;

import com.hyzs.orm2.utils.ProxyUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperInvocationHandler implements InvocationHandler {


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String sql= ProxyUtils.getSql(method);
        Class<?> clazz=ProxyUtils.getReturnType(method);
        return ProxyUtils.exeQuery(sql,args,clazz);
    }
}
