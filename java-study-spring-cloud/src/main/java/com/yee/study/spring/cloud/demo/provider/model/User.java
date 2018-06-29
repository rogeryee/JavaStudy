package com.yee.study.spring.cloud.demo.provider.model;

import java.math.BigDecimal;

/**
 * @author Roger.Yi
 */
public class User {

    private long id;

    private String name;

    private int age;

    private BigDecimal balance = BigDecimal.ZERO;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
