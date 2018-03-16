package com.yee.study.java.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 基于DelimiterBasedFrameDecoder的通道初始化类
 *
 * A decoder that splits the received ByteBufs by one or more delimiters. It is particularly useful for decoding the frames which ends with a delimiter such as NUL or newline characters.
 *
 * DelimiterBasedFrameDecoder allows you to specify more than one delimiter. If more than one delimiter is found in the buffer, it chooses the delimiter which produces the shortest frame. For example, if you have the following data in the buffer:
 *
 * +--------------+
 * | ABC\nDEF\r\n |
 * +--------------+
 *
 * a DelimiterBasedFrameDecoder(Delimiters.lineDelimiter()) will choose '\n' as the first delimiter and produce two frames:
 * +-----+-----+
 * | ABC | DEF |
 * +-----+-----+
 *
 * @author Roger.Yi
 */
public class DelimiterDecoderInitializer extends ChannelInitializer<SocketChannel>
{
    private ByteBuf delimiter;

    public DelimiterDecoderInitializer(ByteBuf delimiter)
    {
        this.delimiter = delimiter;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception
    {
        channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, this.delimiter));
        channel.pipeline().addLast(new StringDecoder());
        channel.pipeline().addLast(new SimpleServerHandler());
    }
}
