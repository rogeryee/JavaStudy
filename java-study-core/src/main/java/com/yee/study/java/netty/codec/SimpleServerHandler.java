package com.yee.study.java.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 用于接受客户端数据
 *
 * @author Roger.Yi
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter
{
    /**
     * 输出接收到的数据到控制台
     * 
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        if(msg instanceof ByteBuf)
        {
            ByteBuf in = (ByteBuf) msg;
            byte[] content = new byte[((ByteBuf) msg).readableBytes()];
            try
            {
                in.readBytes(content);
                print(new String(content));
            }
            finally
            {
                ReferenceCountUtil.release(msg);
                content = null;
            }
        }

        if(msg instanceof String)
        {
            print(msg);
        }
    }

    private void print(Object obj)
    {
        System.out.println("Msg from Client : " + obj);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }
}
