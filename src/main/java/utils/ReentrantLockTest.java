package utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Lz
 * @date 2021/12/30 15:58
 * @since 1.0.0
 */
public class ReentrantLockTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);
//        ReentrantLock lock = new ReentrantLock();
        new Thread(() -> {

            try {
                lock.lock();
                System.out.println("A");
                TimeUnit.SECONDS.sleep(2l);
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }


        }, "A").start();
        new Thread(() -> {

            try {
                lock.lock();
                System.out.println("B");
                TimeUnit.SECONDS.sleep(2l);
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }


        }, "B").start();
        new Thread(() -> {

            try {
                lock.lock();
                System.out.println("C");
                TimeUnit.SECONDS.sleep(2l);
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }


        }, "C").start();
    }
}
