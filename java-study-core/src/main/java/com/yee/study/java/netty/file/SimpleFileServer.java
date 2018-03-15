package com.yee.study.java.netty.file;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * 使用Netty实现的TCP文件服务器
 *
 * @author Roger.Yi
 */
public class SimpleFileServer
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
                    .childHandler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception
                        {
                            channel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                            channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            channel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                            channel.pipeline().addLast(new SimpleFileHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 100);
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
     * 可以在Terminal上输入 telnet localhost 8089 访问
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        SimpleFileServer server = new SimpleFileServer();
        server.start(DEFAULT_PORT);
    }
}
