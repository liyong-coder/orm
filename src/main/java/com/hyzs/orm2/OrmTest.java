package com.hyzs.orm2;

import com.hyzs.orm2.dbo.Stu;
import com.hyzs.orm2.mapper.StuMapper;

import java.lang.reflect.Proxy;
import java.math.BigInteger;
import java.util.List;

public class OrmTest {
    public static void main(String[] args) {
        MapperInvocationHandler mapperInvocationHandler=new MapperInvocationHandler();
        StuMapper stuMapper = (StuMapper)Proxy.newProxyInstance(OrmTest.class.getClassLoader(), new Class<?>[]{StuMapper.class}, mapperInvocationHandler);
        List<Stu> stus = stuMapper.listBySexCodeAndClazzId(1, 1L);
        for(Stu stu:stus){
            System.out.println(stu);
        }

        List<Stu> stus1 = stuMapper.listByIdAndSexCode(BigInteger.valueOf(1), 1);
        for(Stu stu:stus1){
            System.out.println(stu);
        }
    }
}
