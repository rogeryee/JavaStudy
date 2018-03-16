package com.yee.study.java.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 模拟客户端发送数据
 *
 * @author Roger.Yi
 */
public class BufferedClientHandler extends ChannelInboundHandlerAdapter
{
    private ByteBuf buffer;

    public BufferedClientHandler(ByteBuf buffer)
    {
        this.buffer = buffer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }
}
