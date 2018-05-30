package demo.designpatterns.observer.mine;

/**
 * 
* @ClassName: Client  
* @Description: 客户端类 
* @author gaox  
* @date 2018年5月18日 上午9:41:32
 */
public class Client {

	public static void main(String[] args) {
		
		// 创建主题对象
		ConcreateSubject sub = new ConcreateSubject();
		// 创建观察者对象
		ConcreateObserver ob = new ConcreateObserver();
		// 将观察者对象登记在主题对象中
		sub.attach(ob);
		// 更新主题对象状态
		sub.change("哈哈");
	}
}
