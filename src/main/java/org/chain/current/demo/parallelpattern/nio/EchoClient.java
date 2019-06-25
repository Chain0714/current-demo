package org.chain.current.demo.parallelpattern.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

public class EchoClient {
    private Selector selector;

    public void init(String ip, int port) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        this.selector = SelectorProvider.provider().openSelector();
        channel.connect(new InetSocketAddress(ip, port));
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void working() throws IOException {
        while (true) {
            if (!selector.isOpen()) {
                break;
            }
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                iterator.remove();
                if (sk.isConnectable()) {
                    doConnect(sk);
                } else if (sk.isReadable()) {
                    doRead(sk);
                }
            }
        }
    }

    private void doRead(SelectionKey sk) throws IOException {
        SocketChannel channel = (SocketChannel) sk.channel();
        //创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(100);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("客户端收到消息：" + msg);
        channel.close();
        sk.selector().close();
    }

    private void doConnect(SelectionKey sk) throws IOException {
        SocketChannel channel = (SocketChannel) sk.channel();
        //如果正在连接，则完成连接
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        channel.write(ByteBuffer.wrap("hello server\r\n".getBytes()));
        channel.register(selector, SelectionKey.OP_READ);
    }
}
