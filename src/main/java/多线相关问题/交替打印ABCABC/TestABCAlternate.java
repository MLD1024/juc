package 多线相关问题.交替打印ABCABC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，要求输出的结果必须按顺序显示。
 * 如：ABCABCABC…… 依次递归 这个线程陷入了死锁
 */
public class TestABCAlternate {
    private int number = 1; //当前正在执行线程的标记
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition pa = lock.newCondition();
        Condition pb = lock.newCondition();
        Condition pc = lock.newCondition();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                lock.lock();
                try {
                    System.out.print("A ");
                    pb.signal();
                    pa.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                lock.lock();
                try {
                    System.out.print("B ");
                    pc.signal();
                    pb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                lock.lock();
                try {
                    System.out.println("C");
                    pa.signal();
                    pc.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }
}
