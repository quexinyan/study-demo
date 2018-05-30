package demo.designpatterns.adapter;

/**
 * 
* @ClassName: V5PowerAdapter  
* @Description: 适配器，将200V电压转为5V (对象适配器模式) 
* @author gaox  
* @date 2018年5月22日 上午11:20:30
 */
public class V5PowerAdapter implements V5Power{
	
	// 源对象
	private V220Power v220Power;
	
	public V5PowerAdapter(V220Power v220Power) {
		// TODO Auto-generated constructor stub
		this.v220Power = v220Power;
	}

	// 目标接口实现
	@Override
	public int privodeV5Power() {
		// TODO Auto-generated method stub
		// 源对象操作
		int power = v220Power.provideV220Power();
		// power经过各种操作 ---> 5V
		System.out.println("适配器：我悄悄的适配了电压。");
		return 5;
	}
}
