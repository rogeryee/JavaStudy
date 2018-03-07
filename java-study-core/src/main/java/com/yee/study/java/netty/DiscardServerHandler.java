package com.yee.study.java.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 用于接受客户端数据
 *
 * @author Roger.Yi
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter
{
    /**
     * 只是忽略用户请求（仅仅打印）
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        //ByteBuf是一个引用计数对象实现ReferenceCounted，他就是在有对象引用的时候计数+1，无的时候计数-1，当为0对象释放内存
        ByteBuf in = (ByteBuf) msg;
        try
        {
            while (in.isReadable())
            {
                System.out.println((char) in.readByte());
                System.out.flush();
            }
        }
        finally
        {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }
}
