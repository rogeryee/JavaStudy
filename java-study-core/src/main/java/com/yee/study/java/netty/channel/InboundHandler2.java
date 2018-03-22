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
public class InboundHandler2 extends ChannelInboundHandlerAdapter
{
    private static Logger logger = LoggerFactory.getLogger(InboundHandler2.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        logger.info("InboundHandler2.channelRead: ctx :" + ctx);
        String content = ChannelUtil.readContent((ByteBuf) msg);
        System.out.println(content);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        logger.info("InboundHandler2.channelReadComplete");
        ctx.flush();
    }
}
