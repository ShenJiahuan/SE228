package com.shenjiahuan.eBook.config;

import org.springframework.boot.system.ApplicationHome;
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
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getDir();
        String path = jarFile.toString();
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations("file:" + path + "/static/images/");
        //System.out.println(path);
    }

}
