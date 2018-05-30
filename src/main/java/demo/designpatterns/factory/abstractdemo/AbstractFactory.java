package demo.designpatterns.factory.abstractdemo;

/**
 * 
* @ClassName: AbstractFactory  
* @Description: 抽象工厂  
* @author gaox  
* @date 2018年5月21日 上午10:19:10
 */
public interface AbstractFactory {

	// 创建CPU对象
	public Cpu createCpu();
	
	// 创建主板对象
	public MainBoard createMainBoard();
}
