package demo.designpatterns.strategy;

/**
 * 
* @ClassName: PrimaryMemberStrategt  
* @Description: 初级会员折扣类  
* @author gaox  
* @date 2018年5月21日 下午4:38:18
 */
public class PrimaryMemberStrategt implements MemberStrategy{

	@Override
	public double calcPrice(double booksPrice) {
		// TODO Auto-generated method stub
		System.out.println("对于初级会员的没有折扣");
		return booksPrice;
	}

}
