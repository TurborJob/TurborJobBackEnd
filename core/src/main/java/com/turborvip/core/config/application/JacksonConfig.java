package com.turborvip.core.config.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turborvip.core.constant.CommonConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(CommonConstant.FORMAT_DATE_DETAIL);
        return objectMapper;
    }
}
