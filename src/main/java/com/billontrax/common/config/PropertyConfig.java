package com.billontrax.common.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app-config")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyConfig {
    private String AppUrl;
}
