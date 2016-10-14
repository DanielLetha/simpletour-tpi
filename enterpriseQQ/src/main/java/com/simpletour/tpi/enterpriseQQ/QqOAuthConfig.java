package com.simpletour.tpi.enterpriseQQ;

import com.simpletour.tpi.enterpriseQQ.oauth.api.QqApi;
import com.simpletour.tpi.enterpriseQQ.oauth.service.OAuthServiceDeractor;
import com.simpletour.tpi.enterpriseQQ.oauth.service.QqOAuthService;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.SignatureType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Brief :  ${用途}
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/10/14 15:51
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
@Configuration
@ComponentScan(basePackages = {"com.simpletour.tpi.enterpriseQQ"})
@ConfigurationProperties(value = ("classpath:application.properties"))
public class QqOAuthConfig {

//    private static final String CALLBACK_URL = "%s/oauth/%s/callback";

    @Value("${oAuth.qq.app_id}")
    String qqAppId;

    @Value("${oAuth.qq.app_secret}")
    String qqAppSecret;

    @Value("${demo.callback}")
    String callback;

    @Bean
    public QqApi qqApi(){
        return new QqApi();
    }

    @Bean
    public OAuthServiceDeractor getQqOAuthService(){
        return new QqOAuthService(new ServiceBuilder()
                .provider(qqApi())
                .apiKey(qqAppId)
                .apiSecret(qqAppSecret)
                .callback(callback)
                .signatureType(SignatureType.QueryString)
                .build());
    }
}
