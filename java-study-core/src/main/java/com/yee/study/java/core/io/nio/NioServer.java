package com.yee.study.java.core.io.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 采用NIO的服务端示例
 * 
 * @author Roger
 */
public class NioServer
{
    private static final Logger logger = LoggerFactory.getLogger(NioServer.class);
    
    private int BLOCK = 4096; //缓冲区大小

    private ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK); // 接受数据缓冲区

    private ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK); // 发送数据缓冲区

    private Selector selector;

    public NioServer(int port) throws IOException
    {
        // 打开服务器套接字通道, 并设置为非阻塞
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        // 检索与此通道关联的服务器套接字， 并进行服务的绑定
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));

        // 通过open()方法找到Selector
        this.selector = Selector.open();

        // 注册到selector，等待连接
        serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
        logger.info("NIOServer:Server Start at port 8888");
    }

    /**
     * 启动
     * @throws IOException
     * @throws InterruptedException
     */
    private void start() throws IOException, InterruptedException
    {
        while (true)
        {
            // 选择一组键，并且相应的通道已经打开
            this.selector.select();

            // 返回此选择器的已选择键集。
            Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext())
            {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                this.handleKey(selectionKey);
            }
            TimeUnit.SECONDS.sleep(5);
        }
    }

    /**
     * 处理请求
     * @param selectionKey
     * @throws IOException
     */
    private void handleKey(SelectionKey selectionKey) throws IOException
    {
        // 接受请求
        ServerSocketChannel server = null;
        SocketChannel client = null;
        String receiveText;
        String sendText;

        // 测试此键的通道是否已准备好接受新的套接字连接。
        if (selectionKey.isAcceptable())
        {
            // 返回为之创建此键的通道。
            server = (ServerSocketChannel) selectionKey.channel();

            // 接受到此通道套接字的连接。
            // 此方法返回的套接字通道（如果有）将处于阻塞模式。
            client = server.accept();

            // 配置为非阻塞
            client.configureBlocking(false);

            // 注册到selector，等待连接
            client.register(this.selector, SelectionKey.OP_READ);
        }
        else if (selectionKey.isReadable())
        {
            // 返回为之创建此键的通道。
            client = (SocketChannel) selectionKey.channel();

            //将缓冲区清空以备下次读取
            this.receivebuffer.clear();

            //读取服务器发送来的数据到缓冲区中
            int count = client.read(this.receivebuffer);
            if (count > 0)
            {
                receiveText = new String(this.receivebuffer.array(), 0, count);
                client.register(selector, SelectionKey.OP_WRITE);
                logger.info("NIOServer: Receive data from client --> " + receiveText);
            }
        }
        else if (selectionKey.isWritable())
        {
            //将缓冲区清空以备下次写入
            this.sendbuffer.clear();

            // 返回为之创建此键的通道。
            client = (SocketChannel) selectionKey.channel();
            sendText = "message from server";

            //向缓冲区中输入数据
            this.sendbuffer.put(sendText.getBytes());
            this.sendbuffer.flip(); //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位

            //输出到通道
            client.write(this.sendbuffer);
            client.register(selector, SelectionKey.OP_READ);
            logger.info("NIOServer:Send data to client --> " + sendText);
        }
    }
    
    public static void main(String[] args) throws IOException, InterruptedException
    {
        int port = 8888;
        NioServer server = new NioServer(port);
        server.start();
    }
}

