package demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;



/**
 * 
* @ClassName: UrlToMap  
* @Description: url参数处理 
* @author gaox  
* @date 2018年5月16日 下午3:18:04
 */
public class UrlRequestDeal {

	public static void main(String[] args) {
		
		Map<String, Object> retMap = getUrlParams("aa=11&bb=22&cc=33");
		for(Map.Entry<String, Object> entry : retMap.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		
		// map转json字符串
		String retJsonStr = JSONObject.toJSONString(retMap);
		System.out.println(retJsonStr);
	}
	
	/**
	 * 
	 * @Title: getUrlParams   
	 * @Description: 将url参数转换成map   
	 * @author gaox  
	 * @date 2018年5月16日 下午3:26:36
	 * @param: @param param aa=11&bb=22&cc=33
	 */
	public static Map<String, Object> getUrlParams(String param){
		Map<String, Object> map = new HashMap<String, Object>(0);
		if (StringUtils.isBlank(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}
}
