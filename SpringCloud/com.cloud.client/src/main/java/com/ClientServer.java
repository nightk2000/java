package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
//@EnableEurekaClient
@EnableDiscoveryClient			// 配置中心客户端，@EnableDiscoveryClient或者@EnableEurekaClient后者比较单一，前者兼容zk
@SpringBootApplication
@EnableCircuitBreaker			// 断路器
@EnableFeignClients				// 开启 Feign
@EnableTransactionManagement			// 开启事务管理
@EnableAsync(proxyTargetClass=true)		// 配置代理为cglib代理，默认使用 的是jdk动态代理
public class ClientServer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ClientServer.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ClientServer.class, args);
	}
	
}
