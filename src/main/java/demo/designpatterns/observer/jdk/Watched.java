package demo.designpatterns.observer.jdk;

import java.util.Observable;

/**
 * 
* @ClassName: Watched  
* @Description: 被观察者
* @author gaox  
* @date 2018年5月18日 上午10:43:52
 */
public class Watched extends Observable{

	private String data = "初始数据";

	public String getData() {
		return data;
	}

	public void setData(String data) {
		
		if(!this.data.equals(data)) {
			this.data = data;
			this.setChanged(); // 只有调用了此方法，才会调用notifyObservers()发生通知操作
		}
		this.notifyObservers();
	}
}
