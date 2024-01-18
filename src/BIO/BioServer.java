package BIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5432);
            System.out.println("---"+Thread.currentThread().getName()+"等待连接");


            while (true) {
                System.out.println("----------");
                //accept()方法是阻塞的，所以如果没有客户端连接就会一直阻塞在这里
                final Socket socket = server.accept();
                System.out.println("---"+Thread.currentThread().getName()+"有客户端连接");

                //另启动一个线程处理业务逻辑
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("------"+Thread.currentThread().getName());
                        handler(socket);
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//
    public static void handler(Socket socket){


        //字符流
        BufferedReader reader=null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
                String s = reader.readLine();
                System.out.println(s);
//                while(true){
//                    int read = inputStream.read(bytes);
//                    if(read!=-1){
//                        System.out.println(new String(bytes,0,read));
//                    }else{
//
//                        break;
//                    }
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


    }

}
