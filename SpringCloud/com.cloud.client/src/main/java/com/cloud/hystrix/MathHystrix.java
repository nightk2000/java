package com.cloud.hystrix;

import org.springframework.stereotype.Component;

import com.cloud.feign.MathClient;

@Component
public class MathHystrix implements MathClient {

	@Override
	public String math(Integer number) {
		return "fail";
	}

	@Override
	public Object table() {
		return "";
	}

	
	
}
