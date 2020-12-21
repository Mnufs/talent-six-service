package com.talent.six.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix="cors-config")
public class AllowedOriginConfig {

    private String[] allowedOrigins;

}
