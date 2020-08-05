package juc_locks锁框架.Lock接口;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;

/**
 * 〈自定义自旋锁〉
 *
 * @author LZ
 * @date 2020/5/18
 * @since 1.0.0
 */
public class CustomSpinLock {


    private AtomicReference<Thread> lock = new AtomicReference<Thread>();


    public void tryLock() {
        Thread thread = Thread.currentThread();
        while (!lock.compareAndSet(null, thread)) {

        }
        System.out.printf("获取锁的线程是%s\n", thread.getName());
    }

    public void unLock() {
        Thread thread = Thread.currentThread();
        lock.compareAndSet(thread, null);
        System.out.printf("释放锁的线程是%s\n", thread.getName());
    }

    public static void main(String[] args) {
        CustomSpinLock lock = new CustomSpinLock();
        new Thread(() -> {
            try {
                lock.tryLock();
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {

                lock.unLock();
            }
        }, "A").start();
        new Thread(() -> {
            try {
                lock.tryLock();
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unLock();
            }
        }, "B").start();
    }
}
