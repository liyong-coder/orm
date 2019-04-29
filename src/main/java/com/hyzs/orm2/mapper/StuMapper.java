package com.hyzs.orm2.mapper;

import com.hyzs.orm2.dbo.Stu;

import java.math.BigInteger;
import java.util.List;


public interface StuMapper {


    public List<Stu> listBySexCodeAndClazzId(Integer sexCode, Long clazzId);


    public List<Stu> listByIdAndSexCode(BigInteger id,Integer sexCode);

    //public List<Clazz> listById(BigInteger id);
}
