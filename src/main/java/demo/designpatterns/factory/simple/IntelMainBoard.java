package demo.designpatterns.factory.simple;

public class IntelMainBoard implements MainBoard{

	// cpu插槽的孔数
	private int cpuHoles = 0;
	
	// 构造方法，传入cpu插槽的孔数
	public IntelMainBoard(int cpuHoles) {
		this.cpuHoles = cpuHoles;
	}
	
	@Override
	public void installCPU() {
		System.out.println("Intel主板的CPU插槽孔数是：" + cpuHoles);		
	}

}
