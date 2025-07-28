package com.billontrax.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ProfileConfig {
    @Value("${spring.profiles.default}")
    private String defaultProfile;
}
