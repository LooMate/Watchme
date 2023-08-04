package com.easy2remember.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.SQLException;

@EnableTransactionManagement
@Configuration
@PropertySource("classpath:database.properties")
public class DataBaseConfig implements TransactionManagementConfigurer {


    @Value("${db.driverClassName}")
    private String driverClassName;


    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.pass}")
    private String password;

    @Value("classpath:db/user_schema.sql")
    public Resource userSchemaScript;

    @Value("classpath:db/post_schema.sql")
    public Resource postsSchemaScript;

    @Value("classpath:db/role_schema.sql")
    public Resource rolesSchemaScript;

    @Value("classpath:db/oauth_schema.sql")
    public Resource oauthSchemaScript;


    /**
     * It is a bean post-processor that resolves placeholders, across the application
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    /**
     * Setting the default transactionManager
     */
    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return this.txManager();
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = null;
        Class<? extends Driver> driver = null;
        try {
            dataSource = new SimpleDriverDataSource();
            driver = (Class<? extends Driver>) Class.forName(this.driverClassName);

            dataSource.setDriverClass(driver);
            dataSource.setUrl(this.url);
            dataSource.setUsername(this.username);
            dataSource.setPassword(this.password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(this.dataSource());
    }


    @Bean
    public DatabasePopulator databasePopulator() throws SQLException {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(this.userSchemaScript);
        populator.addScript(this.postsSchemaScript);
        populator.addScript(this.rolesSchemaScript);
        populator.addScript(this.oauthSchemaScript);
        populator.populate(dataSource().getConnection());
        return populator;
    }

}


