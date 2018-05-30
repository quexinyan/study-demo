package demo.designpatterns.observer.mine;

import java.util.ArrayList;
import java.util.List;

/**
 * 
* @ClassName: Subject  
* @Description: 抽象主题(Subject)角色 /抽象被观察者(Observable)角色
* 	抽象主题角色把所有对观察者对象的引用保存在一个聚集（比如ArrayList对象）里，每个主题都可以有任何数量的观察者。
* 	抽象主题提供一个接口，可以增加和删除观察者对象，抽象主题角色又叫做抽象被观察者(Observable)角色。
* @author gaox  
* @date 2018年5月17日 下午5:54:44
 */
public abstract class Subject {

	// 用来保存注册的观察者对象
	private List<Observer> list = new ArrayList<Observer>();
	
	// 注册观察者对象
	public void attach(Observer observer) {
		list.add(observer);
		System.out.println("注册了一个观察者对象");
	}
	
	// 删除观察者对象
	public void detach(Observer observer) {
		list.remove(observer);
		System.out.println("删除了一个观察者对象");
	}
	
	// 通知所有观察者对象
	public void nodifyObservers(String state) {
		for(Observer ob : list) {
			ob.update(state);
		}
	}
}
