package com.yee.study.java.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 用于接受客户端数据
 *
 * @author Roger.Yi
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter
{
    /**
     * 接收到客户端请求后发送一条消息后关闭连接
     * @param ctx
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx)
    {
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        final ChannelFuture f = ctx.writeAndFlush(time);
        f.addListener((ChannelFutureListener) future -> {
            assert f == future;
            ctx.close();
        });
    }
}
