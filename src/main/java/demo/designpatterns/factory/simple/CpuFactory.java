package demo.designpatterns.factory.simple;

/**
 * 
* @ClassName: CpuFactory  
* @Description: CPU工厂  
* @author gaox  
* @date 2018年5月18日 下午1:37:35
 */
public class CpuFactory {

	public static Cpu createCPU(int type) {
		Cpu cpu = null;
		if(type == 1) {
			cpu = new IntelCpu(755);
		}else if(type == 2) {
			cpu = new AmdCpu(938);
		}
		
		return cpu;
	}
}
