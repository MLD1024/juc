package juc_locks锁框架.ReadWriteLock接口;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 〈使用ReentrantReadWriteLock控制对TreeMap的访问〉
 * （利用读锁控制读操作的访问，利用写锁控制修改操作的访问），
 * 将TreeMap包装成一个线程安全的集合，并且利用了读写锁的特性来提高并发访问)。
 *
 * @author LZ
 * @create 2020/3/24
 * @since 1.0.0
 */
public class RWTreeMap {
    private final Map<String, Object> m = new TreeMap<String, Object>();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public Object get(String key) {
        r.lock();
        try {
            return m.get(key);
        } finally {
            r.unlock();
        }
    }

    public String[] allKeys() {
        r.lock();
        try {
            return (String[]) m.keySet().toArray();
        } finally {
            r.unlock();
        }
    }

    public Object put(String key, Object value) {
        w.lock();
        try {
            return m.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public void clear() {
        w.lock();
        try {
            m.clear();
        } finally {
            w.unlock();
        }
    }
}