package hello;

import java.util.ArrayList;
import java.util.List;

public class Hello {

    public static void main(String[] args) throws Throwable {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
                //System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());
            }
        }).start();
//        try {
//            List<Integer> list = new ArrayList<>();
//            //while (true) {
//                add(list);
//                //list=new ArrayList<>();
//            //}
//        } catch (Throwable e) {
//            System.out.println("oom");
//        }
        List<Integer> list = new ArrayList<>();
        add(list);
        System.out.println("main线程运行完了");
    }

    public static void add(List<Integer> list) throws InterruptedException {

        int i = 1;
        try {

            while (true) {

                System.out.println(Thread.currentThread().getName() + ":" + i);
                list.add(i);

                i++;
            }
        }
        catch (Throwable e) {

//            Iterator<Integer> iterator = list.iterator();
//            while (iterator.hasNext()) {
//                iterator.next();
//                    iterator.remove();
//                }
//
//            System.out.println(list.size());
            //Thread.sleep(1000);

            throw e;
        } finally {
            System.out.println("finally");

        }

    }
}
