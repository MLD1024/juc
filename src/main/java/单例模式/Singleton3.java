package 单例模式;

/**
 * @author MND
 * @date 2019/3/25
 * @describe 懒汉式 匿名内部类的方式
 * 在内部类被加载和初始化时，才创建instance实例对象
 * 静态内部类不会自动随着内部类的夹杂和初始化而初始化，它是要单独去加载和初始化的
 * 因为是在内部类加载和初始化时，创建的，因此是线程安全的  静态内部类实现模式（线程安全，调用效率高，可以延时加载）
 */
public class Singleton3 {
    private Singleton3(){

    }
    private static class Inner{
        private static final Singleton3 instance = new Singleton3();
    }
    public static Singleton3 getInstance(){
        return Inner.instance;
    }
}
