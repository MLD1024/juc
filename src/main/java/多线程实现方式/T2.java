package 多线程实现方式;

/**
 * @author MND
 * @date 2019/3/25
 * @describe
 */
public class T2 extends Thread {
    @Override
    public void run() {
        System.out.println("你好！");
    }

    public static void main(String[] args) {
        new T2().start();
    }
}
