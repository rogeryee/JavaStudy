package com.yee.study.java.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author Roger.Yi
 */
public class ChannelUtil
{
    public static String readContent(ByteBuf buffer)
    {
        byte[] content = new byte[buffer.readableBytes()];
        buffer.readBytes(content);
        buffer.release();
        return new String(content);
    }

    public static ByteBuf writeContent(String content)
    {
        ByteBuf buffer = Unpooled.copiedBuffer(content.getBytes());
        return buffer;
    }
}
