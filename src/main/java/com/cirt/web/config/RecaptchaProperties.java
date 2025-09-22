package com.cirt.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "recaptcha")
@Getter
@Setter
public class RecaptchaProperties {
    private String siteKey;
    private String secretKey;
    private String verifyUrl;
}
