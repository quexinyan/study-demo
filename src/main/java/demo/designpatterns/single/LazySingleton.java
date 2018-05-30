package demo.designpatterns.single;

/**
 * 
* @ClassName: LazySingleton  
* @Description: 懒汉式-使用时才创建  
* @author gaox  
* @date 2018年5月21日 下午1:34:18
 */
public class LazySingleton {

	private static LazySingleton instance = null;
	
	public LazySingleton() {}
	
	public static synchronized LazySingleton getInstance() {
		if(instance == null) {
			instance = new LazySingleton();
		}
		return instance;
	}
}
