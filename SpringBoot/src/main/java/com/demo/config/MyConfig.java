package com.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//@PropertySource(value= {"application.properties"})	// 该写法会导致junit找不到配置文件，更换为以下写法
@PropertySource("classpath:application.properties")
public class MyConfig {

	
	@Value("${my.config.username}")
	private String username;
	
	@Value("${my.config.password}")
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
