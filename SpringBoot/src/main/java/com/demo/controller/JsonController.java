package com.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.aop.AopHandle;
import com.demo.interfaces.DemoService;

@RestController
public class JsonController {

	@Autowired
	DemoService demoService;
	
	@RequestMapping("/body.json")
	public Object json(HttpServletRequest req,HttpServletResponse res)
	{
		return demoService.obj(req.getRequestURI(), req.getRequestURL().toString());
	}
	
	@RequestMapping("/json/{key}.json")
	@AopHandle( msg = "AOP日志记录" )
	public Object jsondata(HttpServletRequest req,HttpServletResponse res, @PathVariable String key)
	{
		return demoService.obj(key, req.getRequestURI(), req.getRequestURL().toString());
	}
	
	@RequestMapping("/table.json")
	public Object tables(HttpServletRequest req,HttpServletResponse res)
	{
		return demoService.tables(0);
	}
	
	@RequestMapping("/table/{index}.json")
	public Object tablesIndex(HttpServletRequest req,HttpServletResponse res,@PathVariable int index)
	{
		return demoService.tables(index);
	}
}
