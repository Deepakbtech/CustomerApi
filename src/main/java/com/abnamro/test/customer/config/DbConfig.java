package com.abnamro.test.customer.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("prod")
@Configuration
@PropertySource(value = "classpath:database.properties")
public class DbConfig {

}
