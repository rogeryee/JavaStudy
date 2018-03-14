package com.yee.study.java.netty.http.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 使用Netty实现的Http服务器
 *
 * @author Roger.Yi
 */
public class HttpServer
{
    private static final int DEFAULT_PORT = 8089;

    public void start(int port) throws Exception
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try
        {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleServerChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync(); // 应用程序会一直等待,直到channel关闭
        }
        finally
        {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    /**
     * 可以在浏览器上输入 http://localhost:8089/test.do?a=1&b=2 访问
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        HttpServer server = new HttpServer();
        server.start(DEFAULT_PORT);
    }
}
