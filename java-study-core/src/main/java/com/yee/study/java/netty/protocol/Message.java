package com.yee.study.java.netty.protocol;

/**
 * 消息定义类
 * 
 * @author Roger.Yi
 */
public final class Message
{
    /**
     * 消息头
     */
    private Header header;

    /**
     * 消息体
     */
    private Object body;

    @Override
    public String toString()
    {
        return "Message{" + "header=" + header + "}";
    }

    public Header getHeader()
    {
        return header;
    }

    public void setHeader(Header header)
    {
        this.header = header;
    }

    public Object getBody()
    {
        return body;
    }

    public void setBody(Object body)
    {
        this.body = body;
    }
}
