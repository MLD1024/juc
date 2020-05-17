package 线程池;

import java.util.concurrent.*;

/**
 * @author MND
 * @date 2019/3/26
 * @describe
 */
public class TestThreadPool {
    public static void main(String[] args) throws InterruptedException {
//        testCachedPool();
//        testSingleThreadPool();
//        testScheduledPool();
//        testFixedThreadPool();
//        testFuture();
//        testWorkStealingPool();
//        testAbortPolicy();
//        testCallerRunsPolicy();
//        testDiscardOldestPolicy();
//        testDiscardPolicy();
        testCustomRejectedExecutionHandler();
    }

    /**
     * 测试自定义线程池的拒绝策略
     */
    private static void testCustomRejectedExecutionHandler() throws InterruptedException {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(1,
                3,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                (Runnable r, ThreadPoolExecutor e) -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + "添加开始");
                        e.getQueue().put(r);
                        System.out.println(Thread.currentThread().getName() + "添加成功");
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });
        int i = 0;
        try {
            for (; i < 100; i++) {

                int finalI = i;
                pool.execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("任务 %d 姓名 %s \n", finalI, Thread.currentThread().getName());
                });

                System.out.printf("===============任务 %d 添加队列 \n", finalI, Thread.currentThread().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
            pool.awaitTermination(100000, TimeUnit.SECONDS);
            System.out.println(i);
        }
    }

    /**
     * 测试线程拒绝策略
     */
    private static void testDiscardPolicy() throws InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1,
                3,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
        int i = 0;
        try {
            for (; i < 100; i++) {

                int finalI = i;
                pool.execute(() -> {
                    System.out.printf("任务 %d 姓名 %s \n", finalI, Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.SECONDS);
            System.out.println(i);
        }
    }


    /**
     * 测试线程拒绝策略
     */
    public static void testDiscardOldestPolicy() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1,
                3,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        int i = 0;
        try {
            for (; i < 100; i++) {

                int finalI = i;
                pool.execute(() -> {
                    System.out.printf("任务 %d 姓名 %s \n", finalI, Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(i);
            pool.shutdown();
        }

    }

    /**
     * 测试线程拒绝策略
     */
    public static void testCallerRunsPolicy() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1,
                3,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        int i = 0;
        try {
            for (; i < 100; i++) {
                pool.execute(() -> {
                    System.out.println("test:  " + Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(i);
            pool.shutdown();
        }

    }

    /**
     * 测试线程拒绝策略
     */
    public static void testAbortPolicy() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1,
                3,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        int i = 0;
        try {
            while (true) {
                i++;
                pool.execute(() -> {
                    System.out.println("test");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(i);
            pool.shutdown();
        }

    }

    //缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
    public static void testCachedPool() {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 2; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        service.shutdown();
    }

    //创建单个线程池。线程池中只有一个线程
    public static void testSingleThreadPool() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            final int j = i;
            service.execute(() -> {
                System.out.println(j + "" + Thread.currentThread().getName());
            });
        }
        service.shutdown();
    }

    // 创建固定大小的线程，可以延迟或定时的执行任务。
    public static void testScheduledPool() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        service.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName());
        }, 0, 500, TimeUnit.MILLISECONDS);
        service.shutdown();
    }

    // 创建固定大小的线程池
    public static void testFixedThreadPool() {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 6; i++) {
            service.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        service.shutdown();
    }

    //测试Future类
    public static void testFuture() {
        ExecutorService service = Executors.newFixedThreadPool(5);
        FutureTask<String> task = new FutureTask<String>(() -> {
            return "落霞与孤鹜齐飞，秋水共长天一色！";
        });
        service.submit(task);
        try {
            String s = task.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }


    //创建一个带并行级别的线程池，并行级别决定了同一时刻最多有多少个线程在执行，如不穿如并行级别参数，将默认为当前系统的CPU个数。下面用代码来体现这种并行的限制，从结果中可以看到，同一时刻只有两个线程执行。
    public static void testWorkStealingPool() {
        ExecutorService service = Executors.newWorkStealingPool();
        service.execute(() -> {
            System.out.println(1000 + " " + Thread.currentThread().getName());
        });
        service.execute(() -> {
            System.out.println(2000 + " " + Thread.currentThread().getName());
        });
        service.execute(() -> {
            System.out.println(3000 + " " + Thread.currentThread().getName());
        });
        service.execute(() -> {
            System.out.println(4000 + " " + Thread.currentThread().getName());
        });
        service.execute(() -> {
            System.out.println(5000 + " " + Thread.currentThread().getName());
        });
    }


}
