package com.shenjiahuan.eBook;

import com.shenjiahuan.eBook.config.HibernateConfig;
import com.shenjiahuan.eBook.property.FileStorageProperties;
import com.shenjiahuan.eBook.util.HibernateUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude= {UserDetailsServiceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableConfigurationProperties({FileStorageProperties.class})
public class EbookApplication {

    public static void main(String[] args) {

        HibernateUtil.initialize();

        SpringApplication.run(EbookApplication.class, args);
    }

}
