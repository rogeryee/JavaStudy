package com.yee.study.java.netty.http.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.URI;

/**
 * @author Roger.Yi
 */
public class HttpClient
{
    private static final String DEFAULT_HOST = "127.0.0.1";

    private static final int DEFAULT_PORT = 8089;

    public static void main(String[] args) throws Exception
    {
        HttpClient client = new HttpClient();
//        client.submit(buildPostRequest());
        client.submit(buildGetRequest());
    }

    /**
     * 构建HTTP.POST请求
     * @return
     * @throws Exception
     */
    private static HttpRequest buildPostRequest() throws Exception
    {
        URI uri = new URI("http://127.0.0.1:8089/getform/");
        String msg = "Are you ok?";
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri.toASCIIString(), Unpooled
                .wrappedBuffer(msg.getBytes("UTF-8")));
        request.headers().set(HttpHeaderNames.HOST, DEFAULT_HOST);
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderNames.CONNECTION);
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        return request;
    }

    /**
     * 构建HTTP.GET请求
     * @return
     * @throws Exception
     */
    private static HttpRequest buildGetRequest() throws Exception
    {
        URI uri = new URI("http://127.0.0.1:8089/getform?a=1&b=2");
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString());
        request.headers().set(HttpHeaderNames.HOST, DEFAULT_HOST);
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderNames.CONNECTION);
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        return request;
    }

    /**
     * 模拟Http请求
     *
     * @throws Exception
     */
    public void submit(HttpRequest request) throws Exception
    {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>()
            {
                @Override
                public void initChannel(SocketChannel ch) throws Exception
                {
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new SimpleClientHandler());
                }
            });
            ChannelFuture f = b.connect(DEFAULT_HOST, DEFAULT_PORT).sync();
            f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();
        }
        finally
        {
            workerGroup.shutdownGracefully();
        }
    }
}
