package com.cloud.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.dao.DemoDao;
import com.cloud.interfaces.DemoService;

@Service
public class DemoServiceImpl implements DemoService {

	@Autowired
	DemoDao demoDao;
	
	@Override
	public Object obj(String... keys) {
		return keys;
	}

	@Override
	public String str(String... keys) {
		return Arrays.toString(keys);
	}

	@Transactional(value="txManager")	// 事务管理支持
	@Override
	public List<?> tables(int index){
		if( index==1 ) {
			return demoDao.tables1();
		}else if( index==2 ) {
			return demoDao.tables2();
		}else {
			return demoDao.tables();
		}
	}
	
}
