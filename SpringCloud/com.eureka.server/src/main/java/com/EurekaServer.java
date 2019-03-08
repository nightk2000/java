package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//@EnableAutoConfiguration	// 允许自动配置
//注册 SpringCloud Eureka 服务
@EnableEurekaServer
//注册 SpringBoot 启动器, exclude 表示启动时不进行配置检测
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class EurekaServer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(EurekaServer.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EurekaServer.class, args);
	}
	
}
