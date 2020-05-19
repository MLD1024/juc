package juc_locks锁框架.Condition接口;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 〈缓冲队列的示例〉
 * 假定有一个缓冲队列，支持 put 和 take 方法。如果试图在空队列中执行 take 操作，
 * 则线程将一直阻塞，直到队列中有可用元素；
 * 如果试图在满队列上执行 put 操作，
 * 则线程也将一直阻塞，直到队列不满。
 *
 * @author LZ
 * @create 2020/3/24
 * @since 1.0.0
 */
class BoundedBuffer {

    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
    private Object[] items = new Object[100];

    private int putptr;
    private int takeptr;
    private int count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) { //防止虚假唤醒，Condition的await调用一般会放在一个循环判断中
                notFull.await();
            }
            items[putptr] = x;
            if (++putptr == items.length) {
                putptr = 0;
            }
            ++count;
            notEmpty.signal();

        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length) {
                takeptr = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}