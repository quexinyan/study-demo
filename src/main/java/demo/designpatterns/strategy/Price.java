package demo.designpatterns.strategy;

/**
 * 
* @ClassName: Price  
* @Description: 价格类
* @author gaox  
* @date 2018年5月21日 下午5:12:33
 */
public class Price {

	// 持有一个具体策略的对象
	private MemberStrategy memberStrategy;
	
	public Price(MemberStrategy memberStrategy) {

		this.memberStrategy = memberStrategy;
	}

	// 计算图书价格
	public double quote(double booksPrice) {
		return memberStrategy.calcPrice(booksPrice);
	}
	
}
