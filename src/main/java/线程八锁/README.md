### 自旋锁 ( 采用循环的方式去尝试获取锁 可以减少上下文切换的消耗 消耗cpu )
``` 
 do{
  } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
```