package utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Lz
 * @date 2021/12/8 14:46
 * @since 1.0.0
 */
public class LockSupportTest {
    static Object lock = new Object();

    public static void main(String[] args) {
        // park 状态
        new Thread(() -> {
            LockSupport.park();

        }, "park").start();


        LockSupport.unpark(Thread.currentThread());
    }

    public void testPark() {

    }

    public static void state() {
        // runnable 状态
        new Thread(() -> {
            test();

        }, "wait").start();

        // sleep 状态
        new Thread(() -> {
            try {
                Thread.sleep(100000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "sleep").start();


        // runnable 状态
        new Thread(() -> {
            while (true) {

            }

        }, "runnable").start();


        // 等待 状态
        new Thread(() -> {
            test();

        }, "synchronized").start();

        // park 状态
        new Thread(() -> {
            LockSupport.park();

        }, "park").start();


    }


    public static void test() {
        synchronized (lock) {
            try {

                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}

class FIFOMutex {
    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<Thread>();

    public void lock() {
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        // Block while not first in queue or cannot acquire lock
        while (waiters.peek() != current ||
                !locked.compareAndSet(false, true)) {
            LockSupport.park(this);
            if (Thread.interrupted()) // ignore interrupts while waiting
                wasInterrupted = true;
        }

        waiters.remove();
        // reassert interrupt status on exit
        if (wasInterrupted) {
            current.interrupt();
        }

    }

    public void unlock() {
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }
}