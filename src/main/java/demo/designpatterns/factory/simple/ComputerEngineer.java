package demo.designpatterns.factory.simple;

/**
 * 
* @ClassName: ComputerEngineer  
* @Description: 组装  
* @author gaox  
* @date 2018年5月18日 下午2:49:31
 */
public class ComputerEngineer {

	// 定义组装机需要的cpu
	private Cpu cpu = null;
	// 定义组装机需要的主板
	private MainBoard board = null;
	
	public void makeComputer (int cupType, int mainboardType) {
		
		// 1.首先准备好装机所需要的配件
		prepareHardwares(cupType, mainboardType);
		
		// 2.组装机器
		// 3.测定机器
		// 4.交付客户
	}
	
	public void prepareHardwares(int cupType, int mainboardType) {
		//这里要去准备CPU和主板的具体实现，为了示例简单，这里只准备这两个
        //可是，装机工程师并不知道如何去创建，怎么办呢？
		
		// 直接找相应的工厂获取
		this.cpu = CpuFactory.createCPU(cupType);
		this.board = MainBoardFactory.createMainBoard(mainboardType);
		
		// 测试配件是否好用
		this.cpu.caculate();
		this.board.installCPU();
	}
}
