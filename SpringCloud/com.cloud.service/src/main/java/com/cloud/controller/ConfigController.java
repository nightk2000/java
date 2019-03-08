package com.cloud.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
public class ConfigController {

	@Value("${spring.config.demo}")
	private String str;
	
	@RequestMapping("/config.json")
	public Object config(HttpServletRequest req,HttpServletResponse res)
	{
		return JSONObject.toJSON(str);
	}
	
}
