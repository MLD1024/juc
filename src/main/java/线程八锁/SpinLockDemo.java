package 线程八锁;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 〈自定义自旋锁〉
 *
 * @author LZ
 * @date 2020/5/15
 * @since 1.0.0
 */
class ShareData {

    private AtomicInteger flag = new AtomicInteger(0);

    public void change1() {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            while (!flag.compareAndSet(0, 1)) {
                System.out.println(flag.get());
            }
        } finally {
            lock.unlock();
        }
    }

    public void change0() {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            while (!flag.compareAndSet(1, 0)) {
                System.out.println(flag.get());

            }
        } finally {
            lock.unlock();
        }
    }

}

public class SpinLockDemo {
    public static void main(String[] args) {
       final ShareData shareData = new ShareData();
        new Thread(shareData::change0).start();
        new Thread(shareData::change1).start();
    }
}
