package demo.fenye;

import java.util.ArrayList;
import java.util.List;

/**
 * 
* @ClassName: ListPage  
* @Description: list本地分页  
* @author gaox  
* @date 2018年6月7日 下午2:01:00
 */
public class ListPage {

	public static void main(String[] args) {
		
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 1; i <= 30; i++) {
			list.add(i);
		}
		
		// 第几页
		int pageNo = 3;
		// 每页条数
		int pageSize = 10;
		int fromIndex = pageSize * (pageNo - 1);  
        int toIndex = pageSize * pageNo;
        
        if(toIndex > list.size()) {
        	toIndex = list.size();
        }
        if(fromIndex > toIndex) {
        	fromIndex = toIndex;
        }
        System.out.println(fromIndex+","+toIndex);   
        System.out.println(list.subList(fromIndex, toIndex));
	}
}
