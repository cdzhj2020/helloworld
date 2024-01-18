package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class ChatClient {


    private static  final int PORT=8513;
    private SocketChannel socketChannel;
    private String userName;
    private Selector selector;

    public ChatClient(){

        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            userName = socketChannel.getLocalAddress().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendInfo(String info){
        String str=userName+"说："+info;
        try {
            ByteBuffer buffer = ByteBuffer.allocate(16);
            buffer.put(info.getBytes());
            buffer.flip();
            socketChannel.write(buffer);

            // socketChannel.write(ByteBuffer.wrap(str.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInfo(){

        int select = 0;
        try {
            select = selector.select();
            if (select > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if(key.isReadable()){
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        System.out.println(new String(buffer.array()));

                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        ChatClient chatClient = new ChatClient();
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Thread.sleep(3000);
                        chatClient.readInfo();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {

            String s = sc.nextLine();
            chatClient.sendInfo(s);
        }
    }
}
