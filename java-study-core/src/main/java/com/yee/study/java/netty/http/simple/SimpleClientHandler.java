package com.yee.study.java.netty.http.simple;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Roger.Yi
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter
{
    private static final Logger logger = LoggerFactory.getLogger(SimpleClientHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        logger.info("Read msg, className = " + msg.getClass().getName());
        
        if (msg instanceof HttpContent)
        {
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf buffer = httpContent.content();
            byte[] content = new byte[buffer.readableBytes()];
            buffer.readBytes(content);
            logger.info("Response from Server is : \r\n" + new String(content));
            buffer.release();
            ctx.close();
        }
    }
}
