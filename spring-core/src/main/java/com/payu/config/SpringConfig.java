package com.payu.config;


import com.payu.util.ByeUtil;
import com.payu.util.HelloUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by akesh.patil on 21-02-2017.
 */

@Configuration
public class SpringConfig {

    @Bean(name = "helloUtil")
    public HelloUtil getHelloUtil() {
        return new HelloUtil();
    }

    @Bean(name = "byeUtil")
    public ByeUtil getByeUtil() {
        return new ByeUtil();
    }
}
