package utils;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author Lz
 * @date 2021/12/30 15:58
 * @since 1.0.0
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);

        new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println("A");
                TimeUnit.SECONDS.sleep(2l);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }, "A").start();
        new Thread(() -> {

            try {
                semaphore.acquire();
                System.out.println("B");
                TimeUnit.SECONDS.sleep(2l);
                semaphore.release();
            } catch (Exception ignore) {
            }


        }, "B").start();
        new Thread(() -> {

            try {
                semaphore.acquire();
                System.out.println("C");
                TimeUnit.SECONDS.sleep(2l);
                semaphore.release();
            } catch (Exception ignore) {

            }


        }, "C").start();
    }
}
