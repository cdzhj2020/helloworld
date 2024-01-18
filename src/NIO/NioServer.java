package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {


    public static void main(String[] args) {

        try {
            SocketAddress address = new InetSocketAddress("127.0.0.1", 5671);
            ServerSocketChannel server = ServerSocketChannel.open();
            server.socket().bind(address);
            // ServerSocketChannel bind = server.bind(address);
            Selector selector = Selector.open();
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                if (selector.select(10000) == 0) {
                    continue;
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        Thread.sleep(15);
                        SocketChannel socketChannel = server.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        long i = channel.read(buffer);
                        if (i != -1) {
                            System.out.println(new String(buffer.array()));
                        }
                        buffer.flip();
                    }
                    iterator.remove();
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {

        }

    }
}
