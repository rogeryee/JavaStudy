package com.yee.study.java.core.io.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 采用NIO的服务端示例
 *
 * @author Roger
 */
public class NioServer {

    private static final Logger logger = LoggerFactory.getLogger(NioServer.class);

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    public NioServer(int port) throws IOException {
        this.serverSocketChannel = ServerSocketChannel.open(); // 打开 Server Socket Channel
        this.serverSocketChannel.configureBlocking(false); // 配置为非阻塞
        this.serverSocketChannel.socket().bind(new InetSocketAddress(port)); // 绑定 Server port

        this.selector = Selector.open(); // 创建 Selector
        this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 注册 Server Socket Channel 到 Selector
        logger.info("Server started.");

        handleKeys();
    }

    /**
     * 处理事件
     *
     * @throws IOException
     */
    private void handleKeys() throws IOException {
        while (true) {
            // 通过 Selector 选择 Channel
            int selectNums = selector.select(10 * 1000L);
            if (selectNums == 0) {
                continue;
            }

            // 遍历可选择的 Channel 的 SelectionKey 集合
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove(); // 移除下面要处理的 SelectionKey

                // 忽略无效的 SelectionKey
                if (!key.isValid()) {
                    continue;
                }

                handleKey(key);
            }
        }
    }

    private void handleKey(SelectionKey key) throws IOException {
        // 接受连接就绪
        if (key.isAcceptable()) {
            handleAcceptableKey(key);
        }
        // 读就绪
        if (key.isReadable()) {
            handleReadableKey(key);
        }
        // 写就绪
        if (key.isWritable()) {
            handleWritableKey(key);
        }
    }

    /**
     * 处理接受连接事件
     *
     * @param key
     * @throws IOException
     */
    private void handleAcceptableKey(SelectionKey key) throws IOException {
        SocketChannel clientSocketChannel = ((ServerSocketChannel) key.channel()).accept(); // 接受 Client Socket Channel
        clientSocketChannel.configureBlocking(false); // 配置为非阻塞
        clientSocketChannel.register(selector, SelectionKey.OP_READ, new ArrayList<String>()); // 注册 Client Socket Channel 到 Selector
        logger.info("Accept new channel");
    }

    /**
     * 处理可读事件
     *
     * @param key
     * @throws IOException
     */
    private void handleReadableKey(SelectionKey key) throws IOException {

        // 从客户端SocketChannel中读取数据
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();
        ByteBuffer readBuffer = CodecUtil.read(clientSocketChannel);

        // 处理连接已经断开的情况
        if (readBuffer == null) {
            logger.info("Disconnect channel");
            clientSocketChannel.register(selector, 0);
            return;
        }

        if (readBuffer.position() > 0) {
            String content = CodecUtil.newString(readBuffer);
            logger.info("Read data：" + content);

            // 添加到响应队列
            List<String> responseQueue = (ArrayList<String>) key.attachment();
            responseQueue.add("Response：" + content);

            // 注册 Client Socket Channel 到 Selector
            clientSocketChannel.register(selector, SelectionKey.OP_WRITE, key.attachment());
        }
    }

    /**
     * 处理可写事件
     *
     * @param key
     * @throws ClosedChannelException
     */
    private void handleWritableKey(SelectionKey key) throws ClosedChannelException {
        // 向客户端SocketChannel写入数据
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();

        // 遍历响应队列
        List<String> responseQueue = (ArrayList<String>) key.attachment();
        for (String content : responseQueue) {
            logger.info("Write data：" + content);
            CodecUtil.write(clientSocketChannel, content);
        }
        responseQueue.clear();

        // 注册 Client Socket Channel 到 Selector
        clientSocketChannel.register(selector, SelectionKey.OP_READ, responseQueue);
    }

    public static void main(String[] args) throws IOException {
        NioServer server = new NioServer(8888);
    }
}

