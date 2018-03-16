package com.yee.study.java.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 基于FixedLengthFrameDecoder的通道初始化类
 *
 * A decoder that splits the received ByteBufs by the fixed number of bytes. For example, if you received the following four fragmented packets:
 * +---+----+------+----+
 * | A | BC | DEFG | HI |
 * +---+----+------+----+
 *
 * A FixedLengthFrameDecoder(3) will decode them into the following three packets with the fixed length:
 * +-----+-----+-----+
 * | ABC | DEF | GHI |
 * +-----+-----+-----+
 *
 * @author Roger.Yi
 */
public class FixLengthDecoderInitializer extends ChannelInitializer<SocketChannel>
{
    private int frameLength;

    public FixLengthDecoderInitializer(int frameLength)
    {
        this.frameLength = frameLength;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception
    {
        channel.pipeline().addLast(new FixedLengthFrameDecoder(frameLength));
        channel.pipeline().addLast(new StringDecoder());
        channel.pipeline().addLast(new SimpleServerHandler());
    }
}
