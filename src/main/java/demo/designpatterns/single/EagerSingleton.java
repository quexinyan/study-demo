package demo.designpatterns.single;

/**
 * 
* @ClassName: EagerSingleton  
* @Description: 饿汉式-先创建，需要的时候直接用  
* @author gaox  
* @date 2018年5月21日 下午1:29:03
 */
public class EagerSingleton {

	private static EagerSingleton instance = new EagerSingleton();
	
	public EagerSingleton() {}
	
	public static EagerSingleton getInstance() {
		return instance;
	}
}
