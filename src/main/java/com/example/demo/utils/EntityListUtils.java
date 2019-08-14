package com.example.demo.utils;


import com.example.demo.config.TestConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EntityListUtils {
    private static final Logger logger = LoggerFactory.getLogger(EntityListUtils.class);

    @Autowired
    private TestConfigProperties configPropertiesAutowired;
    public static TestConfigProperties configProperties;

    @PostConstruct
    public void init() {
        System.out.println(11111);
        configProperties = this.configPropertiesAutowired;
    }
}
