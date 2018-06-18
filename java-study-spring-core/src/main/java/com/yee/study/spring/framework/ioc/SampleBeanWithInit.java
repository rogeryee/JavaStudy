package com.yee.study.spring.framework.ioc;

import org.springframework.beans.factory.InitializingBean;

/**
 * 自定义初始化方法的Bean（采用实现InitializingBean接口）
 * @author Roger.Yi
 */
public class SampleBeanWithInit implements InitializingBean
{
    private String title = "";

    public void afterPropertiesSet() throws Exception
    {
        this.title = "hello world";
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}

/**
 * 自定义初始化方法的Bean（使用自定义的初始化方法）
 */
class SampleBeanWithInitMethod
{
    private String title = "";

    public void init()
    {
        this.title = "Hi Roger";
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
