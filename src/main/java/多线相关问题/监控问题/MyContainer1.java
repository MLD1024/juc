package 多线相关问题.监控问题;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author MND
 * @date 2019/3/25
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * 分析下面这个程序，能完成这个功能吗？
 */
public class MyContainer1 {
    private List<Integer> list = new ArrayList<>();
    public void add(int arr){
        list.add(arr);
    }
    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        MyContainer1 c = new MyContainer1();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                System.out.println("添加第"+i+"个");
                c.add(i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();
        new Thread(()->{
            while (true){
                if(c.size()==5){
                    System.out.println("到了第五个了");
                    break;
                }
            }
        },"t2").start();
    }
}
