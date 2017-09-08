package com.yee.study.java.designpattern.prototype;

import java.io.*;

/**
 * 形状类
 *
 * @author Roger.Yi
 */
public abstract class Shape implements Cloneable, Serializable
{
    private String id;

    protected String type;

    abstract void draw();

    /**
     * 浅复制
     * ø
     * @return
     */
    public Object clone()
    {
        Object clone = null;
        try
        {
            clone = super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return clone;
    }

    /**
     * 深复制
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object deepClone() throws IOException, ClassNotFoundException {

        /* 写入当前对象的二进制流 */
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);

        /* 读出二进制流产生的新对象 */
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
