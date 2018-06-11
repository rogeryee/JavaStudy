package com.yee.study.spring.framework.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 示例Bean
 * 用于展示Spring如何初始化Bean对象
 *
 * @author Roger.Yi
 */
public class SampleBean
{
    private static Logger logger = LoggerFactory.getLogger(SampleBean.class);

    /**
     * 属性 - beanName
     */
    private SampleBeanAttr beanName;

    /**
     * 属性 - beanDesc
     */
    private SampleBeanAttr beanDesc;

    public SampleBean()
    {
    }

    public SampleBean(SampleBeanAttr beanName)
    {
        this.beanName = beanName;
    }

    public void sayHello()
    {
        logger.info(this.toString() + ":Say Hello!");
    }

    @Override
    public String toString()
    {
        return "SampleBean [" + getAttr("name", beanName) + getAttr("desc", beanDesc) + "]";
    }

    private String getAttr(String name, SampleBeanAttr attr)
    {
        return "name : " + (beanName == null || beanName.getName() == null ? "N/A" : beanName.getName()) + " ";
    }

    // Getters and Setters
    public SampleBeanAttr getBeanName()
    {
        return beanName;
    }

    public void setBeanName(SampleBeanAttr beanName)
    {
        this.beanName = beanName;
    }

    public SampleBeanAttr getBeanDesc()
    {
        return beanDesc;
    }

    public void setBeanDesc(SampleBeanAttr beanDesc)
    {
        this.beanDesc = beanDesc;
    }
}

class SampleBeanAttr
{
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
