package ru.drip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = { "ru.drip.*" })
@PropertySource("classpath:application-test.properties")
public class AcceptanceSpringConfiguration {
    @Bean
    public static RestWorker RestTemplateConfigurer()
    {
        return new RestWorker();
    }

    @Bean
    public static SessionControlBlock SessionControlBlockConfigurer()
    {
        return new SessionControlBlock();
    }
}
