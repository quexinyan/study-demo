package demo.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

/**
 * 
* @ClassName: RquestParamAnalysis  
* @Description: springmvc 请求参数解析demo  
* @author gaox  
* @date 2018年6月1日 下午3:46:35
 */
@RestController
@RequestMapping("/requestParamAnalysis")
public class RequestParamAnalysis {

	/**
	 * 
	 * @Title: transformRequestToPOJO   
	 * @Description: 将请求数据转换成pojo返回
	 * @author gaox  
	 * @throws IOException 
	 * @date 2018年6月1日 下午3:53:25
	 */
	@RequestMapping("/transformRequestToPOJO")
	public Object transformRequestToPOJO(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		/**
		 * req.getInputStream()用于post请求获取数据
		 */
		String paramString = IOUtils.toString(req.getInputStream(), "utf-8");
		System.out.println(paramString);
		Student student = JSONObject.parseObject(paramString, Student.class);
		
		return student;
	}
}
