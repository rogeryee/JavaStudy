package com.yee.study.java.netty.channel;

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
        String response = "Msg from Server : I am ok!";
        ctx.write(ChannelUtil.writeContent(response));
        ctx.flush();
    }
}