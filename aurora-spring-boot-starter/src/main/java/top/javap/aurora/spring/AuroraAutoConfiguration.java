package top.javap.aurora.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/13
 **/
@Configuration
@EnableConfigurationProperties(SpringAuroraConfiguration.class)
public class AuroraAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SpringAuroraInitializer springAuroraInitializer(SpringAuroraConfiguration springAuroraConfiguration, ApplicationContext applicationContext) {
        return new SpringAuroraInitializer(springAuroraConfiguration, applicationContext);
    }
}