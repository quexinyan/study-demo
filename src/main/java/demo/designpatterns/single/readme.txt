单例模式：http://www.cnblogs.com/java-my-life/archive/2012/03/31/2425631.html
特点：
	单例类只能有一个实例。
	单例类必须自己创建自己的唯一实例。
	单例类必须给所有其他对象提供这一实例。
	
	
创建单例的几种方式：
1.饿汉式
2.懒汉式
扩展：
1.双重检查加锁
2.Lazy initialization holder class模式 -类级的内部类
	类级内部类：简单点说，类级内部类指的是，有static修饰的成员式内部类。如果没有static修饰的成员式内部类被称为对象级内部类。
　　			类级内部类相当于其外部类的static成分，它的对象与外部类对象间不存在依赖关系，因此可直接创建。而对象级内部类的实例，是绑定在外部对象实例中的。
　　			类级内部类中，可以定义静态的方法。在静态方法中只能够引用外部类中的静态成员方法或者成员变量。
　　			类级内部类相当于其外部类的成员，只有在第一次被使用的时候才被会装载。
	多线程缺省同步锁：
			在多线程开发中，为了解决并发问题，主要是通过使用synchronized来加互斥锁进行同步控制。但是在某些情况中，JVM已经隐含地为您执行了同步，这些情况下就不用自己再来进行同步控制了。这些情况包括：
			1.由静态初始化器（在静态字段上或static{}块中的初始化器）初始化数据时
	     	2.访问final字段时
　　			3.在创建线程之前创建对象时
　　			4.线程可以看见它将要处理的对象时