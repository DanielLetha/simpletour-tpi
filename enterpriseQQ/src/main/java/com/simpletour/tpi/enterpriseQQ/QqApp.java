package com.simpletour.tpi.enterpriseQQ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Brief :  ${用途}
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/10/14 15:45
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {SecurityConfiguration.class})
public class QqApp  {

    public static void main(String[] args) {
        SpringApplication.run(QqApp.class);
    }
}
