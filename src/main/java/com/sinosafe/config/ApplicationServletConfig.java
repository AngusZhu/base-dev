package com.sinosafe.config;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationServletConfig {

    @Bean
    public ServletRegistrationBean mappingStatViewServlet(){

        StatViewServlet statViewServlet = new StatViewServlet();
        ServletRegistrationBean mappingStatViewServlet = new ServletRegistrationBean(statViewServlet,"/druid/*");
        return  mappingStatViewServlet;
    }



}
