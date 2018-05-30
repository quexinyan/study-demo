package demo.designpatterns.facade;

/**
 * 
* @ClassName: Client  
* @Description: 测试 
* @author gaox  
* @date 2018年5月30日 下午6:23:21
 */
public class Client {

	public static void main(String[] args) {
		Worker it1 = new ITWorker("鸿洋");  
        it1.workOneDay();  
        Worker it2 = new ITWorker("老张");  
        it2.workOneDay();  
        Worker hr = new HRWorker("迪迪");  
        hr.workOneDay();  
        Worker pm = new ManagerWorker("坑货");  
        pm.workOneDay();
	}
}
