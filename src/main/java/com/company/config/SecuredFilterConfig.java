package com.company.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SecuredFilterConfig {

    private final JwtFilter jwtTokenFilter;

    @Bean
    public FilterRegistrationBean<JwtFilter> filterRegistrationBean() {
        FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(jwtTokenFilter);
        bean.addUrlPatterns("/category/adm/*");
        bean.addUrlPatterns("/profile/adm/*");
        bean.addUrlPatterns("/attach/adm/*");
        bean.addUrlPatterns("/tag/adm/*");
        return bean;
    }

}
