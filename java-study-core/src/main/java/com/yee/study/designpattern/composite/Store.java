package com.yee.study.designpattern.composite;

/**
 * 商店类
 * @author Roger.Yi
 */
public abstract class Store
{
    private String name;

    /**
     * 添加店面
     * @param store
     */
    public void add(Store store){}

    /**
     * 删除店面
     * @param store
     */
    public void remove(Store store){}

    /**
     * 消费
     */
    public abstract void pay();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
