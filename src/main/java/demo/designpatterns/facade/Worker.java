package demo.designpatterns.facade;

/**
 * 
* @ClassName: Worker  
* @Description: 定义模板 
* @author gaox  
* @date 2018年5月30日 下午6:12:13
 */
public abstract class Worker {

	protected String name;
	
	public Worker(String name) {
		this.name = name;
	}
	
	/**
	 * 记录一天的工作
	 */
	public final void workOneDay() {
		System.out.println("--------------work start----------------");
		enterCompany();
		computerOn();
		work();
		computerOff();
		exitCompany();
		System.out.println("--------------work end----------------");
	}
	
	// 工作
	public abstract void work();
	
	// 关电脑
	private void computerOff() {
		System.out.println(name + "关闭电脑");
	}
	
	// 开电脑
	private void computerOn() {
		System.out.println(name + "打开电脑");
	}
	
	// 进入公司
	private void enterCompany() {
		System.out.println(name + "进入公司");
	}
	
	// 离开公司
	private void exitCompany() {
		System.out.println(name + "离开公司");
	}
}
