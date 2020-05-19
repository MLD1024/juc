###　Condition
* Condition可以看做是Obejct类的wait()、notify()、notifyAll()方法的替代品，与Lock配合使用。
* 当线程执行condition对象的await方法时，当前线程会立即释放锁，并进入对象的等待区，等待其它线程唤醒或中断。