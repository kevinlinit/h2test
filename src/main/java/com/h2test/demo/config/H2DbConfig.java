package com.h2test.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.File;

/**
 * @ClassName H2DbConfig
 * @Descriptioin
 * @Author linqiyuan
 * @Date 2022/11/28 14:22
 * @Version 1.0
 */
@Configuration
public class H2DbConfig {

    @Bean(name = "h2DataSource")
    public DataSource dataSource() {
        File path = new File("h2db");
        String h2db = "jdbc:h2:" + path.getAbsolutePath() + ".mv.db" + ";MODE=MySQL;DB_CLOSE_ON_EXIT=FALSE";
        return DataSourceBuilder.create().url(h2db).username("SA").password("").build();
    }

    @Bean(name = "h2TransactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }

    @Bean(name = "h2SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("h2DataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sessionFactory.getObject();
    }

    @Bean(name = "h2Template")
    public JdbcTemplate h2Template(@Qualifier("h2DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "primaryDataSourse")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource primaryDataSourse() {
        return DataSourceBuilder.create().build();
    }

    @Bean("primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSourse") DataSource dataSource){

        Mybatis

        return null;
    }


}