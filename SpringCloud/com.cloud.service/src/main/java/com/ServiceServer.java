package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableEurekaClient
@EnableDiscoveryClient			// 配置中心客户端，@EnableDiscoveryClient或者@EnableEurekaClient后者比较单一，前者兼容zk
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableCircuitBreaker			// 断路器
public class ServiceServer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ServiceServer.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceServer.class, args);
	}
	
}
