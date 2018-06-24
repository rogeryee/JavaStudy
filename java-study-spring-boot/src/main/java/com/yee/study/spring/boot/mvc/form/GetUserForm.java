package com.yee.study.spring.boot.mvc.form;

/**
 * 获取User的表单定义类
 * 
 * @author Roger.Yi
 */
public class GetUserForm {

    private String name;

    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}