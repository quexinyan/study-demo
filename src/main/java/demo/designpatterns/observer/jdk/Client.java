package demo.designpatterns.observer.jdk;

/**
 * 
* @ClassName: Client  
* @Description: Client对象首先创建了Watched和Watcher对象。
* 	在创建Watcher对象时，将Watched对象作为参数传入；
* 	然后Client对象调用Watched对象的setData()方法，触发Watched对象的内部状态变化；
* 	Watched对象进而通知实现登记过的Watcher对象，也就是调用它的update()方法。 
* @author gaox  
* @date 2018年5月18日 上午11:22:17
 */
public class Client {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// 创建被观察者
		Watched watched = new Watched();
		// 创建观察者，并将观察者添加到被观察者队列中
		Watcher watcher = new Watcher(watched);
		// 更新被观察者内容
		watched.setData("最新内容1");
		watched.setData("最新内容2");
		watched.setData("最新内容3");
	}
}
