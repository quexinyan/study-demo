package demo.genericity;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 
* @ClassName: GenericityFunction  
* @Description: 泛型方法  
* 	   1）public 与 返回值中间<T>非常重要，可以理解为声明此方法为泛型方法。
 *     2）只有声明了<T>的方法才是泛型方法，泛型类中的使用了泛型的成员方法并不是泛型方法。
 *     3）<T>表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T。
 *     4）与泛型类的定义一样，此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型
* @author gaox  
* @date 2018年6月1日 上午11:18:05
 */
public class GenericityFunction {

	public <T> void out0(T val) {
		System.out.println(val);
	}
	
	public <T> T out1(T val) {
		return val;
	}
	
	public <T> T out2(List<T> list, int index) {
		return list.get(index);
	}
	
	public List<?> out3(List<?> list){
		return list;
	}
	
	@Test
	public void test0() {
		out0("ahhh");
		out0(123456);
	}
	
	@Test
	public void test1() {
		String ret1 = out1("ahhh");
		Integer ret2 = out1(123456);
		System.out.println(ret1);
		System.out.println(ret2);
	}
	
	@Test
	public void test2() {
		List<String> list = new ArrayList<String>();
		list.add("ahhh");
		String ret = out2(list, 0);
		System.out.println(ret);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test3() {
		List<String> listStr = new ArrayList<String>();
		listStr.add("ahhh");
		List<Integer> listInt = new ArrayList<Integer>();
		listInt.add(123456);
		List<String> retStr = (List<String>) out3(listStr);
		System.out.println(retStr.get(0));
		List<Integer> retInt = (List<Integer>) out3(listInt);
		System.out.println(retInt.get(0));
	}
}
