package volatile关键字;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author MND
 * @date 2019/3/25
 * @describe
 */
public class TestVolatile implements Runnable {
    private boolean flag = false;
    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag = " + flag);
    }

    public static void main(String[] args) {
        TestVolatile t = new TestVolatile();
        new Thread(t).start();
        while (true){

            if(t.flag){
                System.out.println("==================");
                break;
            }
        }
    }
}
