package demo.designpatterns.observer.jdk;

import java.util.Observable;
import java.util.Observer;

/**
 * 
* @ClassName: Watcher  
* @Description: 观察者  
* @author gaox  
* @date 2018年5月18日 上午11:18:58
 */
public class Watcher implements Observer{

	// 将观察者对象添加到被观察者对象中
	public Watcher(Observable o) {
		o.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("观察者收到被观察者的通知："+ ((Watched)o).getData());
	}

}
