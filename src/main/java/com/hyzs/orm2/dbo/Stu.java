package com.hyzs.orm2.dbo;

import java.math.BigInteger;

public class Stu {
    private BigInteger id;

    private String stuName;

    private Integer sexCode;

    private BigInteger clazzId;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getSexCode() {
        return sexCode;
    }

    public void setSexCode(Integer sexCode) {
        this.sexCode = sexCode;
    }

    public BigInteger getClazzId() {
        return clazzId;
    }

    public void setClazzId(BigInteger clazzId) {
        this.clazzId = clazzId;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Stu{");
        sb.append("id=").append(id);
        sb.append(", stuName='").append(stuName).append('\'');
        sb.append(", sexCode=").append(sexCode);
        sb.append(", clazzId=").append(clazzId);
        sb.append('}');
        return sb.toString();
    }
}

