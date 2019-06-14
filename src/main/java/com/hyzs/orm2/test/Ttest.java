package com.hyzs.orm2.test;

import java.lang.reflect.Method;
import java.util.List;

public class Ttest {
    public  test(List<Dog> list){
        return null;
    }
    public static void main(String[] args) throws Exception{
      Method method = Ttest.class.getMethod("test",List.class);
      method.getGenericReturnType();
    }
}

class Animal{
}
interface Sing{}

class Dog<T extends Animal&Sing>{
}