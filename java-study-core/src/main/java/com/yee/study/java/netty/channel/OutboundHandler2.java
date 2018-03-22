package com.yee.study.java.netty.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端Outbound处理器
 * @author Roger.Yi
 */
public class OutboundHandler2 extends ChannelOutboundHandlerAdapter
{
    private static Logger logger = LoggerFactory.getLogger(OutboundHandler2.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        logger.info("OutboundHandler2.write");
        super.write(ctx, msg, promise); // 执行下一个OutboundHandler
    }
}
