package volatile关键字;

/**
 * @author MND
 * @date 2019/3/25
 * @describe
 */
public class TestVolatile2 implements Runnable {
    private volatile boolean flag = false;
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
        TestVolatile2 t = new TestVolatile2();
        new Thread(t).start();
        while (true){

            if(t.flag){
                System.out.println("==================");
                break;
            }
        }
    }
}
