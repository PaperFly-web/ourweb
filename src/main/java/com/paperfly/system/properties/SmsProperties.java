package com.paperfly.system.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("sms.config")
public class SmsProperties {
    String accessKeyId;
    String secret;
    String signName;
    String templateCode;
}
