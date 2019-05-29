package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableAutoConfiguration	// 允许自动配置
@SpringBootApplication		// 注册 SpringBoot 启动类
@EnableTransactionManagement			// 开启事务管理
//@EnableAsync(proxyTargetClass=true)		// 配置代理为cglib代理，默认使用 的是jdk动态代理
@ServletComponentScan		// 支持扫描 Servlet3.0 注解
public class BootServer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BootServer.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BootServer.class, args);
	}
	
}
