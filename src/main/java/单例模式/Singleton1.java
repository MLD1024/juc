package 单例模式;

/**
 * @author MND
 * @date 2019/3/25
 * @describe 饿汉式 构造器私有化  自行创建
 */
public class Singleton1 {
    public  static  final  Singleton1 instance = new Singleton1();

    private Singleton1(){

    }
}
