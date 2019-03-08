package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableAutoConfiguration
//@EnableEurekaClient
@EnableDiscoveryClient			// 配置中心客户端，@EnableDiscoveryClient或者@EnableEurekaClient后者比较单一，前者兼容zk
@SpringBootApplication
@EnableCircuitBreaker			// 断路器
//@RefreshScope					// 用于刷新配置
public class ServiceServer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ServiceServer.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceServer.class, args);
	}
	
}
