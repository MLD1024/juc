package 单例模式;

/**
 * @author MND
 * @date 2019/3/25
 * @describe  枚举类（线程安全，调用效率高，不能延时加载，可以天然的防止反射和反序列化调用）
 */
public  enum Singleton4 {
    //枚举元素本身就是单例
    INSTANCE;
    //添加自己需要的操作
    public void singletonOperation() {
    }
}
