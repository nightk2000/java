package com.cloud.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration
@SpringBootConfiguration
@RefreshScope	// 自动刷新
public class DataSourceConfig {

	
    @Bean(name = "txManager")  // 创建事务管理器,给事务管理器命名
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean(name = "mainDataSource")
    @Qualifier("mainDataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource mainDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "oneDataSource")
    @Qualifier("oneDataSource")
    @ConfigurationProperties(prefix="spring.datasource.one")
    public DataSource oneDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "twoDataSource")
    @Qualifier("twoDataSource")
    @ConfigurationProperties(prefix="spring.datasource.two")
    public DataSource twoDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "mainJdbcTemplate")
    @Primary	// 多数据源，此注解指定一个主数据源
    public JdbcTemplate mianJdbcTemplate(@Qualifier("mainDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "oneJdbcTemplate")
    public JdbcTemplate oneJdbcTemplate(@Qualifier("oneDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
    @Bean(name = "twoJdbcTemplate")
    public JdbcTemplate twoJdbcTemplate(@Qualifier("twoDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
