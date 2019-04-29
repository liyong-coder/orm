package com.hyzs.orm2.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProxyUtils {


    /**
     * <PRE>
     *     这里只是获取了List<T>里T的具体的类型
     * </PRE>
     * @param method
     * @return
     */
    public static Class<?> getReturnType(Method method){
        if(!method.getName().startsWith("list")){
            throw new RuntimeException("不支持的方法");
        }
        ParameterizedType returnType =(ParameterizedType) method.getGenericReturnType();
        Type actualTypeArgument = returnType.getActualTypeArguments()[0];
        return (Class<?>)actualTypeArgument;
    }


    /**
     * 获取所有的字段名
     * @param clazz
     * @return
     */
    public static List<String> getFieldNames(Class<?> clazz){
        Field[] declaredFields = clazz.getDeclaredFields();
        List<String> list=new ArrayList<>(declaredFields.length);
        for(Field field:declaredFields){
            list.add(field.getName());
        }
        return list;
    }

    public static final char UNDERLINE='_';
    public static String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }


    public static List<String> getWhereFieldName(Method method){
        String methodName=method.getName();
        String tmp=methodName.replace("listBy","");
        String[] ss = tmp.split("And");
        List<String> list=new ArrayList<>(ss.length);
        for(String s:ss){
            list.add(firstLower(s));
        }
        return list;
    }

    public static String firstLower(String fieldName){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<fieldName.length();i++){
            Character c=fieldName.charAt(i);
            if(i==0){
                sb.append((c+"").toLowerCase());
            }
            else{
                sb.append(c+"");
            }
        }
        return sb.toString();
    }




    public static String getSql(Method method){
        StringBuilder sb=new StringBuilder();
        sb.append("select ");
        Class<?> clazz=getReturnType(method);
        List<String> fieldNames=getFieldNames(clazz);
        sb.append(fieldNames.stream().map(fieldName->camelToUnderline(fieldName)+" as "+fieldName).collect(Collectors.joining(",")));
        sb.append(" from ");
        sb.append(camelToUnderline(firstLower(clazz.getSimpleName())));
        sb.append(" where ");
        List<String> whereFieldNames=getWhereFieldName(method);
        sb.append(whereFieldNames.stream().map(fieldName->camelToUnderline(fieldName)+"=?").collect(Collectors.joining(" and ")));
        return sb.toString();
    }


    public static void setFieldValue(Object stu,String fieldName,Object value){
        Class<?> aClass = stu.getClass();
        try {
            Field field = aClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(stu,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static <T> List<T> exeQuery(String sql,Object[] args,Class<T> clazz) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://172.16.8.18:3306/test?allowMultiQueries=true&useUnicode=true&zeroDateTimeBehavior=convertToNull","root","admin@1");
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for(int i=0;i<args.length;i++){
            System.out.println("args:"+args[i]);
            preparedStatement.setObject(i+1,args[i]);
        }
        ResultSet rs = preparedStatement.executeQuery();
        List<String> columns=getColumns(rs);
        List<T> list=new ArrayList<>();
        while(rs.next()){
            T obj=clazz.newInstance();
            for(String column:columns){
                setFieldValue(obj,column,rs.getObject(column));
            }
            list.add(obj);
        }
        rs.close();
        preparedStatement.close();
        connection.close();
        return list;
    }


    /**
     * 获取结果集中所有的列名
     * @param rs
     * @return
     * @throws Exception
     */
    public static List<String> getColumns(ResultSet rs) throws Exception{
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<String> list=new ArrayList<>();
        for(int i=0;i<columnCount;i++){
            list.add(metaData.getColumnLabel(i+1));
        }
        return list;
    }
}
