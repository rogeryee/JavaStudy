package com.yee.study.java.core.io.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 采用NIO通讯的客户端示例
 *
 * @author Roger
 */
public class NioClient {
    private static final Logger logger = LoggerFactory.getLogger(NioClient.class);

    private SocketChannel clientSocketChannel;

    private Selector selector;

    private final List<String> responseQueue = new ArrayList<String>();

    private CountDownLatch connected = new CountDownLatch(1);

    public NioClient(int port) throws IOException, InterruptedException {
        this.clientSocketChannel = SocketChannel.open(); // 打开 Client Socket Channel
        this.clientSocketChannel.configureBlocking(false); // 配置为非阻塞
        this.selector = Selector.open(); // 创建 Selector
        this.clientSocketChannel.register(selector, SelectionKey.OP_CONNECT); // 注册 Server Socket Channel 到 Selector
        this.clientSocketChannel.connect(new InetSocketAddress(port)); // 连接服务器

        new Thread(() -> {
            try {
                handleKeys();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        if (connected.getCount() != 0) {
            connected.await();
        }
        logger.info("Client 启动完成");
    }

    /**
     * 处理事件
     *
     * @throws IOException
     */
    private void handleKeys() throws IOException {
        while (true) {
            // 通过 Selector 选择 Channel
            int selectNums = selector.select(30 * 1000L);
            if (selectNums == 0) {
                continue;
            }

            // 遍历可选择的 Channel 的 SelectionKey 集合
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove(); // 移除下面要处理的 SelectionKey
                if (!key.isValid()) { // 忽略无效的 SelectionKey
                    continue;
                }

                handleKey(key);
            }
        }
    }

    private synchronized void handleKey(SelectionKey key) throws IOException {
        // 接受连接就绪
        if (key.isConnectable()) {
            handleConnectableKey(key);
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

    private void handleConnectableKey(SelectionKey key) throws IOException {
        // 完成连接
        if (!clientSocketChannel.isConnectionPending()) {
            return;
        }
        clientSocketChannel.finishConnect();
        logger.info("Accpet new channel");

        clientSocketChannel.register(selector, SelectionKey.OP_READ, responseQueue); // 注册 Client Socket Channel 到 Selector
        connected.countDown(); // 标记为已连接
    }

    private void handleReadableKey(SelectionKey key) throws ClosedChannelException {
        // 从客户端Socket Channel读取数据
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();
        ByteBuffer readBuffer = CodecUtil.read(clientSocketChannel);

        if (readBuffer.position() > 0) { // 写入模式下，
            String content = CodecUtil.newString(readBuffer);
            logger.info("Read data：" + content);
        }
    }

    private void handleWritableKey(SelectionKey key) throws ClosedChannelException {
        // Client Socket Channel
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

    public synchronized void send(String content) throws ClosedChannelException {
        // 添加到响应队列
        responseQueue.add(content);
        logger.info("Write data：" + content);

        clientSocketChannel.register(selector, SelectionKey.OP_WRITE, responseQueue); // 注册 Client Socket Channel 到 Selector
        selector.wakeup(); // 本例中使用了每一秒发送一次消息，而服务端采用的是每30秒轮训一次，所以为了保证每次发送客户端的数据能被及时响应，则需要手动唤醒
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        NioClient client = new NioClient(8888);
        for (int i = 0; i < 30; i++) {
            client.send("nihao: " + i);
            Thread.sleep(1000L);
        }
    }
}
