package demo.designpatterns.adapter;

/**
 * 
* @ClassName: Client  
* @Description: 测试  
* @author gaox  
* @date 2018年5月22日 上午11:34:53
 */
public class Client {

	public static void main(String[] args) {
		Mobile mobile = new Mobile();  
        V5Power v5Power = new V5PowerAdapter(new V220Power()) ;   
        mobile.inputPower(v5Power);
	}
}
