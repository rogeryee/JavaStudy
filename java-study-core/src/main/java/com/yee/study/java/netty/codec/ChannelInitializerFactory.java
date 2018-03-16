package com.yee.study.java.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Roger.Yi
 */
public class ChannelInitializerFactory
{
    public static ChannelInitializer<SocketChannel> newDelimiterDecoderInitializer(ByteBuf delimiter)
    {
        return new DelimiterDecoderInitializer(delimiter);
    }

    public static ChannelInitializer<SocketChannel> newFixLengthDecoderInitializer(int frameLength)
    {
        return new FixLengthDecoderInitializer(frameLength);
    }

    public static ChannelInitializer<SocketChannel> newLengthFieldDecoderInitializer(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip)
    {
        return new LengthFieldDecoderInitializer(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
