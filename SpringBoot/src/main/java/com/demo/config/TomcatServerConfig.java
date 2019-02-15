package com.demo.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class TomcatServerConfig {

	/*
	@Bean
	public TomcatServletWebServerFactory setTomcat() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.setContextPath("/"); // 设置项目访问路径
		tomcat.setPort(8080); // 设置项目端口号
		return tomcat;
	}*/
	
	@Bean
	public ConfigurableServletWebServerFactory configurableServletWebServerFactory(){
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.setPort(8080);
		return factory;
	}


}
