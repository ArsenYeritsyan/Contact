package com.crud.contacts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatastoreConfiguration {
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        String urlProperty = System.getProperty("spring.datasource.url");
//
//        if (System.getProperty("docker") != null)
//            dataSource.setUrl(urlProperty.replace("localhost", "mysql"));
//        else dataSource.setUrl(urlProperty);
//
//        return dataSource;
//    }
}
