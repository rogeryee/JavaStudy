package com.yee.study.java.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
public class OutboundHandler2 extends ChannelOutboundHandlerAdapter
{
    private static Logger logger = LoggerFactory.getLogger(OutboundHandler2.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        logger.info("OutboundHandler2.write");
        String response = "Msg from Server : I am ok! [Write by Outbound2]";
        ByteBuf buffer = Unpooled.buffer(response.length() * 4); // 此处需要初始化较大的buffer，不然后续的Handler无法继续添加内容
        buffer.writeBytes(response.getBytes());
        super.write(ctx, buffer, promise); // 执行下一个OutboundHandler
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        logger.info("OutboundHandler2.exception");
        cause.printStackTrace();
        ctx.flush();
    }
}
