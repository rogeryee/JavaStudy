package com.yee.study.mysoa.rpc.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientInHandler extends ChannelInboundHandlerAdapter {

    public StringBuffer message;

    public String sendMsg;

    public NettyClientInHandler(StringBuffer message, String sendMsg) {
        this.message = message;
        this.sendMsg = sendMsg;
    }

    /*
     * @see 当我们连接成功以后会触发这个方法
     * 在这个方法里面完成消息的发送
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf encoded = ctx.alloc().buffer(4 * sendMsg.length());
        encoded.writeBytes(sendMsg.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /*
     * @see 一旦服务端有消息过来，这个方法会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        System.out.println("server response msg：" + new String(result1));
        message.append(new String(result1));
        result.release();
    }
}
