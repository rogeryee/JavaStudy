package com.yee.study.java.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author Roger.Yi
 */
public class Client
{
    public void start(final ChannelHandler handler) throws Exception
    {
        int port = 8089;
        String ip = "127.0.0.1";

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
                    ch.pipeline().addLast(handler);
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(ip, port).sync();
            f.channel().closeFuture().sync();
        }
        finally
        {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception
    {
        /**
         * 与Server中{@link Server#startServerWithDelimiterCodec()} 配合使用
         */
        startClient4DelimiterCodec();

        /**
         * 与Server中{@link Server#startServerWithFixLengthCodec()} 配合使用
         */
//        startClient4FixLengthCodec();

        /**
         * 与Server中{@link Server#startServerWithLengthFieldCodec()} 配合使用
         */
//        startClient4LengthFieldCodec();
    }

    public static void startClient4DelimiterCodec() throws Exception
    {
        String request = "Hello, I am Roger#Here is my food#Please take it#";
        new Client().start(ClientHandlerFactory.newSimpleClientHandler(request));
    }

    public static void startClient4FixLengthCodec() throws Exception
    {
        String request = "Hello, I am Roger.";
        new Client().start(ClientHandlerFactory.newSimpleClientHandler(request));
    }

    public static void startClient4LengthFieldCodec() throws Exception
    {
        String request = "HELLO, WORLD";
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(0xCAFE);
        buf.writeInt(0x00000C);
        buf.writeBytes(request.getBytes()); // new LengthFieldPrepender(4, false)
        new Client().start(ClientHandlerFactory.newBufferedClientHandler(buf));
    }
}
