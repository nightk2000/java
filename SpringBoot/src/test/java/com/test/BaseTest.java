package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.BootServer;
import com.demo.interfaces.DemoService;

@RunWith(SpringRunner.class) 
@SpringBootTest(classes = BootServer.class)
//@WebAppConfiguration
public class BaseTest {


	@Autowired
	private DemoService demoService;
	
	@Test
	public void tst() {
		demoService.testTransactional();
	}
}
