package demo.designpatterns.observer.mine;

/**
 * 
* @ClassName: ConcreateObserver  
* @Description: 具体观察者角色类  
* @author gaox  
* @date 2018年5月17日 下午10:15:16
 */
public class ConcreateObserver implements Observer{
	
	// 观察者的状态
	private String observerState;

	@Override
	public void update(String state) {
		
		// 更新观察者的状态，使其与目标的状态保持一致
		observerState = state;
		System.out.println("被观察者状态为："+observerState);
	}

}
