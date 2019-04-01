package com.demo.interfaces;

import java.util.List;

public interface DemoService {

	public Object obj(String... keys);
	
	public String str(String... keys);
	
	public List<?> tables(int index);
	
	public void testTransactional();
}
