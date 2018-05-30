package demo.designpatterns.facade;

/**
 * 
* @ClassName: ManagerWorker  
* @Description: 具体实现  
* @author gaox  
* @date 2018年5月30日 下午6:22:16
 */
public class ManagerWorker extends Worker{

	public ManagerWorker(String name) {
		super(name);
	}

	@Override
	public void work() {
		System.out.println(name + "打dota...");
	}

}
