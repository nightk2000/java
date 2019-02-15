package com.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.interfaces.DemoService;

@Controller
public class DemoController {

	@Autowired
	DemoService demoService;
	
	@RequestMapping(value="/body.do", method={RequestMethod.GET, RequestMethod.POST}, produces="text/plain")
	// value：     指定请求的实际地址，指定的地址可以是URI Template 模式；
	// method：  指定请求的method类型， GET、POST、PUT、DELETE等；
	// consumes： 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
	// produces:    指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；
	// params： 指定request中必须包含某些参数值是，才让该方法处理。
	// headers： 指定request中必须包含某些指定的header值，才能让该方法处理请求。
	@ResponseBody
	public String body(HttpServletRequest req,HttpServletResponse res)
	{
		String str = demoService.str(req.getRemoteAddr(), req.getRequestURI(), req.getRequestURL().toString());
		return "<html><body>" + str + "</body></html>";
	}
}
