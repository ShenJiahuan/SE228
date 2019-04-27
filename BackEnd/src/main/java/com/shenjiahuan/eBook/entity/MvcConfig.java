package com.shenjiahuan.eBook.entity;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.net.URL;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello").setViewName("hello");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        URL path = MvcConfig.class.getProtectionDomain().getCodeSource().getLocation();
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations(path + "/static/images/");
        System.out.println(path);
    }

}
