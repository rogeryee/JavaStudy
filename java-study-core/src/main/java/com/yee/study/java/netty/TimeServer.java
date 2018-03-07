package com.yee.study.java.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Roger.Yi
 */
public class TimeServer
{
    private int port;

    public TimeServer(int port)
    {
        this.port = port;
    }

    public void start() throws Exception
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>()
            {
                public void initChannel(SocketChannel ch) throws Exception
                {
                    ch.pipeline().addLast(new TimeServerHandler());
                }
            }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        }
        finally
        {
            //资源优雅释放
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 测试方式在terminal下输入 telnet localhost 8088
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        int port = 8088;
        TimeServer server = new TimeServer(port);
        server.start();
    }
}
