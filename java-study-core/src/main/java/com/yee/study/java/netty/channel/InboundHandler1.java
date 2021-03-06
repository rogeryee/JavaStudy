package com.yee.study.java.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端Inbound处理器
 * 
 * @author Roger.Yi
 */
public class InboundHandler1 extends ChannelInboundHandlerAdapter
{
    private static Logger logger = LoggerFactory.getLogger(InboundHandler1.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        logger.info("InboundHandler1.channelRead: ctx :" + ctx);
        ByteBuf buffer = (ByteBuf)msg;
        buffer.writeBytes(" [Read by Inbound1]".getBytes());
        ctx.fireChannelRead(msg); // 通知执行下一个InboundHandler
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        logger.info("InboundHandler1.channelReadComplete");
        ctx.flush();
    }
}
