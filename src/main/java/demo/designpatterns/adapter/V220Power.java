package demo.designpatterns.adapter;

/**
 * 
* @ClassName: V220Power  
* @Description: 现有接口，需要适配的接口
* @author gaox  
* @date 2018年5月22日 上午11:18:29
 */
public class V220Power {
	
	// 家用提供220V
	public int provideV220Power() {
		System.out.println("我提供220V交流电压。");
		return 220;
	}
}
