package com.example.demo.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
//@ConfigurationProperties(prefix = "sq")
@PropertySource("classpath:test.properties")
@ConfigurationProperties(prefix = "test")
@Data
@Component
public class TestConfigProperties {
    public TestConfigProperties() {

    }

    private  String name;
    private String password;


}
