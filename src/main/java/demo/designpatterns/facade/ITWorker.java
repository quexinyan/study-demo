package demo.designpatterns.facade;

/**
 * 
* @ClassName: ITWorker  
* @Description: 具体实现  
* @author gaox  
* @date 2018年5月30日 下午6:19:28
 */
public class ITWorker extends Worker{

	public ITWorker(String name) {
		super(name);
	}

	@Override
	public void work() {
		System.out.println(name + "写程序-测bug-fix bug");
	}

}
