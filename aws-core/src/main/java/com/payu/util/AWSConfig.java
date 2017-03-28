package com.payu.util;

import com.payu.dynamo.DynamoDBClient;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by akesh.patil on 14-03-2017.
 */

@Configuration
@Import(DynamoDBClient.class)
public class AWSConfig {

    @Bean
    public static PropertyPlaceholderConfigurer getAWSPropertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("aws.properties"));
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }
}
