package 多线程实现方式;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author MND
 * @date 2019/3/25
 * @describe
 */
public class T3 implements Callable<String> {
    @Override
    public String call() throws Exception {
        return  "你好";
    }

    public static void main(String[] args) {
        T3 t3 = new T3();
//        由Callable<String>创建一个FutureTask<Integer>对象：
//        注释：FutureTask<String>是一个包装器，它通过接受Callable<String>来创建，它同时实现了Future和Runnable接口。
        FutureTask<String> task = new FutureTask<>(t3);
        //由FutureTask<String>创建一个Thread对象：
        new Thread(task).start();
        try {
            System.out.println(task.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        至此，一个线程就创建完成了。
    }
}
