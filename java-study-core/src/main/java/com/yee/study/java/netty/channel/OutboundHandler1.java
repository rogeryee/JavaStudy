package com.yee.study.java.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端Outbound处理器
 *
 * @author Roger.Yi
 */
public class OutboundHandler1 extends ChannelOutboundHandlerAdapter
{
    private static Logger logger = LoggerFactory.getLogger(OutboundHandler1.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        logger.info("OutboundHandler1.write");
        ByteBuf buffer = (ByteBuf) msg;
        buffer.writeBytes(" [Write by Outbound1]".getBytes());
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        logger.info("OutboundHandler1.exception");
        cause.printStackTrace();
        ctx.flush();
    }
}
