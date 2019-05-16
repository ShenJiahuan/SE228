package com.shenjiahuan.eBook;

import com.shenjiahuan.eBook.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude= {UserDetailsServiceAutoConfiguration.class})
@EnableConfigurationProperties({FileStorageProperties.class})
public class EbookApplication {

    public static void main(String[] args) {

        SpringApplication.run(EbookApplication.class, args);
    }

}
