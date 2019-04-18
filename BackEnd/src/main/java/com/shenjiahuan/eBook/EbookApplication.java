package com.shenjiahuan.eBook;

import com.shenjiahuan.eBook.util.HibernateUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude= {UserDetailsServiceAutoConfiguration.class})
public class EbookApplication {

    public static void main(String[] args) {

        HibernateUtil.initialize();

        SpringApplication.run(EbookApplication.class, args);
    }

}
