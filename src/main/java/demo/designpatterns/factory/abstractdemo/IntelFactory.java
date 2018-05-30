package demo.designpatterns.factory.abstractdemo;

/**
 * 
* @ClassName: IntelFactory  
* @Description: intel工厂实现类 
* @author gaox  
* @date 2018年5月21日 上午10:23:08
 */
public class IntelFactory implements AbstractFactory{

	@Override
	public Cpu createCpu() {
		// TODO Auto-generated method stub
		return new IntelCpu(755);
	}

	@Override
	public MainBoard createMainBoard() {
		// TODO Auto-generated method stub
		return new IntelMainBoard(755);
	}

	
}
