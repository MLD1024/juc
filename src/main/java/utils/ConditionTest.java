package utils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Lz
 * @date 2021/12/10 15:08
 * @since 1.0.0
 */
public class ConditionTest {


    public static void main(String[] args) {
            ReentrantLock lock = new ReentrantLock();
            Condition pa = lock.newCondition();
            Condition pb = lock.newCondition();

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
            },"A").start();
            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    lock.lock();
                    try {
                        System.out.print("B ");
                        pa.signal();
                        pb.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }

                }
            },"B").start();

    }
}
