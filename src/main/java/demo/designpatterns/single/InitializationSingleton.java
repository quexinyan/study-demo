package demo.designpatterns.single;

/**
 * 
* @ClassName: InitializationSingleton  
* @Description: Lazy initialization holder class模式 ： 
* 	这个模式综合使用了Java的类级内部类和多线程缺省同步锁的知识，很巧妙地同时实现了延迟加载和线程安全。  
* @author gaox  
* @date 2018年5月21日 下午2:15:03
 */
public class InitializationSingleton {

	/**
	 * 解决思路：
	 *要想很简单地实现线程安全，可以采用静态初始化器的方式，它可以由JVM来保证线程的安全性。比如前面的饿汉式实现方式。但是这样一来，不是会浪费一定的空间吗？因为这种实现方式，会在类装载的时候就初始化对象，不管你需不需要。
	 *如果现在有一种方法能够让类装载的时候不去初始化对象，那不就解决问题了？一种可行的方式就是采用类级内部类，在这个类级内部类里面去创建对象实例。
	 * 	这样一来，只要不使用到这个类级内部类，那就不会创建对象实例，从而同时实现延迟加载和线程安全。
	 */
	
	public InitializationSingleton() {}
	
	/**
	 * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载
	 */
	private static class SingletonHolder{
		// 静态初始化器，由JVM来保证线程安全
		private static InitializationSingleton instance = new InitializationSingleton();
		
		private static String str;
		static {
			System.out.println("类级内部类单例测试......");
			str = "单例";
		}
	}
	
	/**
	 * 当getInstance方法第一次被调用的时候，它第一次读取SingletonHolder.instance，导致SingletonHolder类得到初始化；
	 * 而这个类在装载并被初始化的时候，会初始化它的静态域，从而创建Singleton的实例，由于是静态的域，因此只会在虚拟机装载类的时候初始化一次，并由虚拟机来保证它的线程安全性。
　　	         这个模式的优势在于，getInstance方法并没有被同步，并且只是执行一个域的访问，因此延迟初始化并没有增加任何访问成本。
	 */
	public static InitializationSingleton getInstance() {
		return SingletonHolder.instance;
	}
	
	public static void main(String[] args) {
		InitializationSingleton instance1 = getInstance();
		InitializationSingleton instance2 = getInstance();
		System.out.println(instance1);
		System.out.println(instance2);
		System.out.println(SingletonHolder.str);
		System.out.println(SingletonHolder.str);
	}
}
