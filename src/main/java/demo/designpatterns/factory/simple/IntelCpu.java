package demo.designpatterns.factory.simple;

public class IntelCpu implements Cpu{

	// cup的针脚数
	private int pins = 0;
	public IntelCpu(int pins) {
		this.pins = pins;
	}
	
	@Override
	public void caculate() {
		System.out.println("Intel CPU的针脚数：" + pins);
	}

}
