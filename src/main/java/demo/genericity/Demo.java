package demo.genericity;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class Demo {

	/**
	 * 
	 * @Title: character   
	 * @Description: 泛型特性  ：
	 * 	java中的泛型只在编译阶段有效，泛型信息不会进入到运行阶段
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void character() {
		
		List<String> stringList = new ArrayList<String>();
		List<Integer> integerList = new ArrayList<Integer>();
		
		Class stringClass = stringList.getClass();
		Class integerClass = integerList.getClass();
		
		if(stringClass.equals(integerClass)) {
			System.out.println("泛型测试--->类型相同");
		}
	}
	
	/**
	 * 
	 * @Title: genericityClass   
	 * @Description: 泛型类   
	 */
	@Test
	public void genericityClass() {
		
		/**
		 * 泛型的类型参数只能是类类型（包括自定义类），不能是简单类型
		 * 传入的实参类型需与泛型的类型参数类型相同，即为Integer
		 */
		GenericityClass<Integer> generitityInteger = new GenericityClass<Integer>(123456);
		GenericityClass<String> generitityString = new GenericityClass<String>("哈哈");
		
		System.out.println(generitityInteger.getKey());
		System.out.println(generitityString.getKey());
		
	}
}
