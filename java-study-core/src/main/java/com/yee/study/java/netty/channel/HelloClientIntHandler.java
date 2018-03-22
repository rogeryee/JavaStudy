package com.yee.study.java.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端处理器
 *
 * @author Roger.Yi
 */
public class HelloClientIntHandler extends ChannelInboundHandlerAdapter
{
    private static Logger logger = LoggerFactory.getLogger(HelloClientIntHandler.class);

    /**
     * 用于处理来自服务端的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        logger.info("HelloClientIntHandler.channelRead");
        String content = ChannelUtil.readContent((ByteBuf) msg);
        System.out.println(content);
        ctx.close();
    }

    /**
     * 当连接建立的时候向服务端发送消息，channelActive事件当连接建立的时候会触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        logger.info("HelloClientIntHandler.channelActive");
        String msg = "Msg from client : Are you ok?";
        ctx.write(ChannelUtil.writeContent(msg));
        ctx.flush();
    }
}
