package demo.swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;


@Controller
@RequestMapping("/manageruser")
@Api(tags="一:管理用户")
public class ManagerUserController {
	
	@ApiOperation(httpMethod = "GET", value = "通过id查询管理信息")
	@RequestMapping("/queryManagerUser")
	public void queryManagerUser(@ApiParam(required=true,name="id",value="查询id")long id){
		try{
			 
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 这个注解表示，该方法不需要生成接口文档
	 * */
	@ApiIgnore  
	@RequestMapping("/remove")
	public String remove(long id){
		return null;
	} 
	
}
