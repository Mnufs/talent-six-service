package com.talent.six.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义拦截器
        // 拦截所有请求进token拦截器，校验token
        registry.addInterceptor(new TokenFilter()).addPathPatterns("/**");
    }

}
