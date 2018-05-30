package demo.designpatterns.factory.simple;

/**
 * 
* @ClassName: MainBoardFactory  
* @Description: 主板工厂  
* @author gaox  
* @date 2018年5月18日 下午2:22:55
 */
public class MainBoardFactory {

	public static MainBoard createMainBoard(int type) {
		MainBoard board = null;
		if(type == 1) {
			board = new IntelMainBoard(755);
		}else if(type == 2) {
			board = new AmdMainBoard(938);
		}
		
		return board;
	}
}
