package demo.designpatterns.strategy;

/**
 * 
* @ClassName: IntermediateMemberStrategy  
* @Description: 中级会员折扣类  
* @author gaox  
* @date 2018年5月21日 下午5:00:06
 */
public class IntermediateMemberStrategy implements MemberStrategy{

	@Override
	public double calcPrice(double booksPrice) {
		// TODO Auto-generated method stub
		System.out.println("对于中级会员的折扣为10%");
		return booksPrice * 0.9;
	}

	
}
