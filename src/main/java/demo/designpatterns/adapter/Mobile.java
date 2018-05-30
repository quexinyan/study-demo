package demo.designpatterns.adapter;

/**
 * 
* @ClassName: Mobile  
* @Description: 手机充电器一般都是5V左右吧，咱天朝的家用交流电压220V，所以手机充电需要一个适配器（降压器）  
* @author gaox  
* @date 2018年5月22日 上午11:14:32
 */
public class Mobile {

	// 充电
	public void inputPower(V5Power v5Power) {
		int power = v5Power.privodeV5Power();
		System.out.println("手机（客户端）：我需要5V电压充电，现在是-->" + power + "V");
	}
}
