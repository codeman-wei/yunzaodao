package com.yzd.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailConfigProperties {
    private String host;

    private String port;

    private String user;

    private String pass;

    private String fromUser;
}
