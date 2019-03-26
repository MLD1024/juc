package 单例模式;

/**
 * @author MND
 * @date 2019/3/25
 * @describe 懒汉式 构造器私有化 静态变量保存这个唯一的实例  线程不安全
 */
public class Singleton2 {
    private static  Singleton2 instance = null;

    private Singleton2(){

    }
    public static Singleton2 getInstance(){
        if (instance==null){
            instance = new  Singleton2();
        }
        return   instance;
    }
}
/**
 * @date 2019/3/25 下午7:11
 * @param
 * @return
 * @describe    双重锁检查机制 饿汉式
 */
class Singleton6{
    private  static  Singleton6 instance = null;
    private  Singleton6(){

    }
    public static  Singleton6 getInstance(){
        if(instance==null){
            synchronized (Singleton6.class) {
                if (instance == null) {
                    instance = new Singleton6();
                }
            }
        }
        return instance;
    }
}
