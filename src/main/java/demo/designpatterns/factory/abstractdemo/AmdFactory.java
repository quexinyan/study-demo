package demo.designpatterns.factory.abstractdemo;

public class AmdFactory implements AbstractFactory{

	@Override
	public Cpu createCpu() {
		// TODO Auto-generated method stub
		return new AmdCpu(938);
	}

	@Override
	public MainBoard createMainBoard() {
		// TODO Auto-generated method stub
		return new AmdMainBoard(938);
	}

}
