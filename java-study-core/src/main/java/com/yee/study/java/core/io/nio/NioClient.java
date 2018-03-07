package com.yee.study.java.core.io.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 采用NIO通讯的客户端示例
 * 
 * @author Roger
 */
public class NioClient
{
    private static final Logger logger = LoggerFactory.getLogger(NioClient.class);
    
    private static int BLOCK = 4096; // 缓冲区大小

    private static ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK); // 接受数据缓冲区

    private static ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK); // 据缓冲区

    private final static InetSocketAddress SERVER_ADDRESS = new InetSocketAddress("localhost", 8888); // 服务器端地址

    public static void main(String[] args) throws IOException, InterruptedException
    {
        // 打开socket通道，并设置为非阻塞方式
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        // 打开选择器，注册连接服务端socket动作
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(SERVER_ADDRESS);

        Set<SelectionKey> selectionKeys;
        Iterator<SelectionKey> iterator;
        SelectionKey selectionKey;
        SocketChannel client;
        String receiveText;
        String sendText;

        while (true)
        {
            //选择一组键，其相应的通道已为 I/O 操作准备就绪。
            //此方法执行处于阻塞模式的选择操作。
            selector.select();

            //返回此选择器的已选择键集。
            selectionKeys = selector.selectedKeys();
            iterator = selectionKeys.iterator();
            while (iterator.hasNext())
            {
                selectionKey = iterator.next();
                if (selectionKey.isConnectable())
                {
                    client = (SocketChannel) selectionKey.channel();
                    // 判断此通道上是否正在进行连接操作。
                    // 完成套接字通道的连接过程。
                    if (client.isConnectionPending())
                    {
                        client.finishConnect();
                        logger.info("NIOClient:Connected to Server!");
                        sendbuffer.clear();
                        sendbuffer.put("Hello,Server".getBytes());
                        sendbuffer.flip();
                        client.write(sendbuffer);
                    }
                    client.register(selector, SelectionKey.OP_READ);
                }
                else if (selectionKey.isReadable())
                {
                    client = (SocketChannel) selectionKey.channel();
                    //将缓冲区清空以备下次读取
                    receivebuffer.clear();
                    //读取服务器发送来的数据到缓冲区中
                    int count = client.read(receivebuffer);
                    if (count > 0)
                    {
                        receiveText = new String(receivebuffer.array(), 0, count);
                        logger.info("NIOClient:Receive data from server --> " + receiveText);
                        client.register(selector, SelectionKey.OP_WRITE);
                    }

                }
                else if (selectionKey.isWritable())
                {
                    sendbuffer.clear();
                    client = (SocketChannel) selectionKey.channel();
                    sendText = "message from client";
                    sendbuffer.put(sendText.getBytes());
                    //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
                    sendbuffer.flip();
                    client.write(sendbuffer);
                    logger.info("NIOClient:Send data to server" + sendText);
                    client.register(selector, SelectionKey.OP_READ);
                }
            }
            selectionKeys.clear();
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
