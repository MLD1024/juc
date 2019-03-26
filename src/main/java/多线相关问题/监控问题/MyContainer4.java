package 多线相关问题.监控问题;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * <p>
 * 给lists添加volatile之后，t2能够接到通知，但是，t2线程的死循环很浪费cpu，如果不用死循环，该怎么做呢？
 * <p>
 * 这里使用wait和notify做到，wait会释放锁，而notify不会释放锁
 * 需要注意的是，运用这种方法，必须要保证t2先执行，也就是首先让t2监听才可以
 * <p>
 * 阅读下面的程序，并分析输出结果
 * 可以读到输出结果并不是size=5时t2退出，而是t1结束时t2才接收到通知而退出
 * 想想这是为什么？
 * <p>
 * notify之后，t1必须释放锁，t2退出后，也必须notify，通知t1继续执行
 * 整个通信过程比较繁琐
 */
public class MyContainer4 {
    private volatile List<Integer> list = new ArrayList<>();

    private void add(int arr) {
        list.add(arr);
    }

    private int size() {
        return list.size();
    }

    public static void main(String[] args) {
        MyContainer4 c = new MyContainer4();

        new Thread(() -> {
            synchronized (c) {
                System.out.println("t2启动了");
                if (c.size() != 5) {
                    try {
                        c.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println("t2停止了");
                c.notify();

            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (c) {
                for (int i = 0; i < 10; i++) {
                    System.out.println("添加第" + i + "个");
                    c.add(i);
                    if(c.size()==5){
                        try {
                            c.notify();
                            c.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();

    }
}
