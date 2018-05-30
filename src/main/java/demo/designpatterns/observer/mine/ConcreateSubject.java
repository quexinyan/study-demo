package demo.designpatterns.observer.mine;

/**
 * 
* @ClassName: ConcreateSubject  
* @Description: 具体主题角色类  
* @author gaox  
* @date 2018年5月18日 上午9:36:38
 */
public class ConcreateSubject extends Subject{

	// 主题角色状态
	private String state;
	
	public void change(String newState) {
		this.state = newState;
		System.out.println("被观察者对象状态改变为："+state);
		// 被观察者对象状态发生改变，通知所有观察者
		this.nodifyObservers(newState);
	}
}
