package com.yee.study.java.netty.http.simple;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 通道初始化类
 * 
 * @author Roger.Yi
 */
public class SimpleServerChannelInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel channel) throws Exception
    {
        // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
        channel.pipeline().addLast(new HttpRequestDecoder());

        // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
        channel.pipeline().addLast(new HttpResponseEncoder());
        channel.pipeline().addLast(new SimpleServerHandler());
//        channel.pipeline().addLast(new ChunkChannelHandler());
    }
}
