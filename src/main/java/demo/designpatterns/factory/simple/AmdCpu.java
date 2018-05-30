package demo.designpatterns.factory.simple;

public class AmdCpu implements Cpu{

	// cpu的针脚数
	private int pins = 0;
	public AmdCpu(int pins) {
		this.pins = pins;
	}
	
	@Override
	public void caculate() {
		System.out.println("AMD CPU的针脚数：" + pins);
	}

}
