package demo.designpatterns.factory.simple;

/**
 * 
* @ClassName: Client  
* @Description: 测试简单工厂 
* @author gaox  
* @date 2018年5月18日 下午3:08:35
 */
public class Client {

	public static void main(String[] args) {
		ComputerEngineer computerEngineer = new ComputerEngineer();
		computerEngineer.makeComputer(1, 1);
	}
}
