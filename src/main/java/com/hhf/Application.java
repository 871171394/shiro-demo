package com.hhf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @author Huang.Hua.Fu
 * @date 2020/7/10 14:21
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan("com.hhf.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
