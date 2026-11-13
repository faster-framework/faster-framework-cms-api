package cn.org.faster.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Faster CMS 应用入口。
 *
 * <p>技术栈：JDK 17 / Spring Boot 4.1.2 / Spring Framework 7.0.10。</p>
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@MapperScan("cn.org.faster.cms.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
