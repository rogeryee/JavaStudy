package com.yee.study.java.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Roger.Yi
 */
public class Server
{
    private ChannelInitializer channelInitializer;
    
    private int port = 8089;

    public Server(ChannelInitializer channelInitializer)
    {
        this.channelInitializer = channelInitializer;
    }

    public void start() throws Exception
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try
        {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(channelInitializer)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        }
        finally
        {
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
        /**
         * 采用分隔符的编码器
         * 可以在控制台输入 Hello, I am Roger#Here is my food#Please take it# （# 为分隔符）
         * 服务端会将消息分拆成3部分：
         *  Hello, I am Roger
         *  Here is my food
         *  Please take it
         * 
         * 与Client{@link Client#startClient4DelimiterCodec()} ()} 配合使用
         */
        startServerWithDelimiterCodec();

        /**
         * 采用定长的编码器
         * 可以在控制台输入 Hello, I am Roger.
         * 服务端会将消息分拆成以下几个部分：
         *  [Hel]
         *  [lo,]
         *  [ I ]
         *  [am ]
         *  [Rog]
         *  [er.]
         *
         * 与Client{@link Client#startClient4FixLengthCodec()} 配合使用
         */
//        startServerWithFixLengthCodec();

        /**
         * 采用Length Field的编码器
         * 可以在控制台输入 Hello, I am Roger.
         * 服务端会将消息分拆成3部分：
         *
         * 与Client{@link Client#startClient4LengthFieldCodec()} 配合使用
         */
//        startServerWithLengthFieldCodec();
    }

    public static void startServerWithDelimiterCodec() throws Exception
    {
        ByteBuf delimiter = Unpooled.copiedBuffer("#".getBytes());
        new Server(ChannelInitializerFactory.newDelimiterDecoderInitializer(delimiter)).start();
    }

    public static void startServerWithFixLengthCodec() throws Exception
    {
        new Server(ChannelInitializerFactory.newFixLengthDecoderInitializer(3)).start();
    }

    public static void startServerWithLengthFieldCodec() throws Exception
    {
        int maxFrameLength = 1024 * 1024 * 100;
        int lengthFieldOffset = 2;
        int lengthFieldLength = 3;
        int lengthAdjustment = 0;
        int initialBytesToStrip = 0;
        new Server(ChannelInitializerFactory.newLengthFieldDecoderInitializer(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip)).start();
    }
}
