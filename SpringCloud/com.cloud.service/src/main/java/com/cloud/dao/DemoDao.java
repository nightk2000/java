package com.cloud.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DemoDao {

	private static final Logger LOG = LoggerFactory.getLogger(DemoDao.class);
	
	@Autowired
	@Qualifier("mainJdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("oneJdbcTemplate")
	JdbcTemplate jdbcTemplate1;
	
	@Autowired
	@Qualifier("twoJdbcTemplate")
	JdbcTemplate jdbcTemplate2;
	
	public List<?> tables()
	{
		
		String sql = "show tables;";

		LOG.debug(sql);
		
		List<?> list = jdbcTemplate.queryForList(sql);
		return list;
	}
	
	public List<?> tables1()
	{
		
		String sql = "show tables;";

		LOG.debug(sql);
		
		List<?> list = jdbcTemplate1.queryForList(sql);
		return list;
	}
	
	public List<?> tables2()
	{
		
		String sql = "show tables;";

		LOG.debug(sql);
		
		List<?> list = jdbcTemplate2.queryForList(sql);
		return list;
	}
}
