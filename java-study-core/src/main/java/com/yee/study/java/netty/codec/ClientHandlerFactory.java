package com.yee.study.java.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;

/**
 * @author Roger.Yi
 */
public class ClientHandlerFactory
{
    public static ChannelHandler newSimpleClientHandler(String request)
    {
        return new SimpleClientHandler(request);
    }

    public static ChannelHandler newBufferedClientHandler(ByteBuf buf)
    {
        return new BufferedClientHandler(buf);
    }
}
