package com.demo.bean;

import java.io.Serializable;

public class DemoBean implements Serializable {

	private static final long serialVersionUID = -5251948240750745747L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
