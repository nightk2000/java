package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableConfigServer
@EnableEurekaClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)		// 注册 SpringBoot 启动类，不检测数据源配置
public class ConfigServer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ConfigServer.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ConfigServer.class, args);
	}
    
	/**
	 * 如果使用git服务，则可将 ConfigServer 集成到 EurekaServer 中
	 * 
	 */
}
