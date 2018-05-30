package demo.designpatterns.strategy;

/**
 * 
* @ClassName: AdvancedMemberStrategy  
* @Description: 高级会员折扣类  
* @author gaox  
* @date 2018年5月21日 下午5:03:16
 */
public class AdvancedMemberStrategy implements MemberStrategy{

	@Override
	public double calcPrice(double booksPrice) {
		// TODO Auto-generated method stub
		System.out.println("对于高级会员的折扣为20%");
		return booksPrice * 0.8;
	}

}
