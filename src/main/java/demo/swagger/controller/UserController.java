package demo.swagger.controller;

import com.alibaba.fastjson.JSONObject;
import demo.swagger.bean.Result;
import demo.swagger.bean.UserVo;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/userController")
@Api(tags = "二：用户信息")
public class UserController {


	@ApiOperation(value = "swagger测试", httpMethod = "GET")
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String test(){

		return "hello swagger";
	}

	@ApiOperation(value = "查询用户列表-分页", httpMethod = "POST", notes = "分页暂时写死")
	@RequestMapping(value = "/queryUsers", method = RequestMethod.POST)
	@ResponseBody
	public Result queryUsers(@ApiParam(required = true, name = "start", value = "起始条数") Integer start,
								 @ApiParam(name = "end", value = "终止条数") Integer end){

		List<UserVo> data = new ArrayList<>();
		UserVo uv = new UserVo();
		uv.setId(1);
		uv.setLoginId("hhh");
		uv.setUserName("asd");
		uv.setUserPass("123456");
		data.add(uv);

		String msg = data.size() > 0 ? "" : "没有查询到相关记录";
		Result result = new Result();
		result.setMsg(msg);
		result.setCode(1);
		result.setData(data);
		return result;
	}

	@RequestMapping(value = "/listCompound", method = RequestMethod.GET)
	@ResponseBody
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "系统错误"),
			@ApiResponse(code = 200, message = "0 成功,其它为错误,返回格式：{code:0,data[{}]},data中的属性参照下方Model", response = UserVo.class) })
	@ApiOperation(httpMethod = "GET", value = "个人信息")
	public String listCompound(
			@ApiParam(required = true, name = "start", value = "start") int start,
			int limit,
			@ApiParam(required = false, name = "userName", value = "名称模糊查询") String userName) {
		List<UserVo> data = new ArrayList<>();
		String msg = data.size() > 0 ? "" : "没有查询到相关记录";
		Result result = new Result();
		result.setMsg(msg);
		result.setCode(0);
		result.setData(data);
		return JSONObject.toJSONString(result);
	}

	@RequestMapping(value = "/save", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	@ApiOperation(httpMethod = "GET", value = "保存用户信息")
	public String saveUser(@RequestBody UserVo user) {
		user.setId(System.currentTimeMillis());
		String msg = "error";
		Result result = new Result();
		result.setCode(0);
		msg = "操作成功";
		result.setMsg(msg);
		return JSONObject.toJSONString(result);
	}

}
