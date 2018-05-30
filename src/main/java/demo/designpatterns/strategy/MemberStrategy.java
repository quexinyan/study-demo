package demo.designpatterns.strategy;

/**
 * 
* @ClassName: MemberStrategy  
* @Description: 抽象折扣类  
* @author gaox  
* @date 2018年5月21日 下午4:35:12
 */
public interface MemberStrategy {

	/**
	 * 
	 * @Title: calcPrice   
	 * @Description: 计算图书的价格   
	 * @author gaox  
	 * @date 2018年5月21日 下午4:36:31
	 * @param: @param booksPrice  图书的原价
	 * @return: double  计算出打折后的价格  
	 * @throws
	 */
	public double calcPrice(double booksPrice);
}
