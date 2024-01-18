package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ChatServer {
    private static  final int PORT=8513;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;


    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();

    }
    public ChatServer(){
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1",PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                if(selector.select(5000)==0){
                    System.out.println("没有事件发生");
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if(key.isAcceptable()){
                        createChannel();
                    }
                    if(key.isReadable()){
                        readInfo(key);
                    }
//                    if(key.isWritable()){
//                        sendInfo(key);
//                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void sendInfo(SelectionKey key) {
//    }

    private void readInfo(SelectionKey key) {
        final SocketChannel channel = (SocketChannel)key.channel();
        try {
            ByteBuffer buffer = ByteBuffer.allocate(8);

            int read = channel.read(buffer);
            if(read>0){
                String message = new String(buffer.array());
                System.out.println("from 客户端 "+channel.getRemoteAddress()+"发送消息："+message);
                transferToClient(channel,message);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress()+"已离线~~~");
                key.cancel();
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    private void transferToClient(SocketChannel self,String message) {
        Set<SelectionKey> keys = selector.keys();
        Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            Channel channel = key.channel();
            if(channel instanceof  SocketChannel && channel!=self) {
                ByteBuffer wrap = ByteBuffer.wrap(message.getBytes());
                try {
                    ((SocketChannel) channel).write(wrap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void createChannel() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            System.out.println(socketChannel.getRemoteAddress()+"上线了~~~");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
