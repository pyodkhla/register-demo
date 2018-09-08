package com.register.demo.user;

import java.math.BigDecimal;

public class ApplicationUserResponse {
    private long id;
    private String referenceCode;
    private String username;
    private String password;
    private String mobileNo;
    private String memberType;
    private BigDecimal salary;

    public ApplicationUserResponse(long id, String referenceCode, String username, String password, String mobileNo, String memberType, BigDecimal salary) {
        this.id = id;
        this.referenceCode = referenceCode;
        this.username = username;
        this.password = password;
        this.mobileNo = mobileNo;
        this.memberType = memberType;
        this.salary = salary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
