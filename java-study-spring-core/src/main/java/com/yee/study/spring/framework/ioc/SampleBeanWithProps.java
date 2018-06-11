package com.yee.study.spring.framework.ioc;

/**
 * 示例Bean
 * 用于展示如何设置Bean的各个属性
 * 
 * @author Roger.Yi
 */
public class SampleBeanWithProps
{
    private String attr1;

    private String attr2;

    @Override
    public String toString()
    {
        return "SampleBeanWithProps{" + "attr1='" + attr1 + '\'' + ", attr2='" + attr2 + '\'' + '}';
    }

    public String getAttr1()
    {
        return attr1;
    }

    public void setAttr1(String attr1)
    {
        this.attr1 = attr1;
    }

    public String getAttr2()
    {
        return attr2;
    }

    public void setAttr2(String attr2)
    {
        this.attr2 = attr2;
    }
}
