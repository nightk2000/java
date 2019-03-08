package com.cloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloud.hystrix.MathHystrix;

@Component
@FeignClient( name = "spring-cloud-service", fallback = MathHystrix.class )
public interface MathClient {

	@RequestMapping(value="/math.json")
	public String math(@RequestParam Integer number);
	
	@RequestMapping(value="/table.json")
	public Object table();
	
}
