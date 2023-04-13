package top.javap.aurora.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import top.javap.aurora.Aurora;
import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.interceptor.AuroraInterceptor;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/13
 **/
public class SpringAuroraInitializer implements InitializingBean {

    private final SpringAuroraConfiguration springAuroraConfiguration;
    private final ApplicationContext applicationContext;

    public SpringAuroraInitializer(SpringAuroraConfiguration springAuroraConfiguration, ApplicationContext applicationContext) {
        this.springAuroraConfiguration = springAuroraConfiguration;
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initAurora();
    }

    private void initAurora() {
        AuroraConfiguration config = Aurora.config();
        config.setCorePoolSize(springAuroraConfiguration.getCorePoolSize());
        config.setMaxPoolSize(springAuroraConfiguration.getMaxPoolSize());
        config.setKeepAliveSeconds(springAuroraConfiguration.getKeepAliveSeconds());
        config.setQueueSize(springAuroraConfiguration.getQueueSize());

        config.setConnectTimeout(springAuroraConfiguration.getConnectTimeout());
        config.setWriteTimeout(springAuroraConfiguration.getWriteTimeout());
        config.setReadTimeout(springAuroraConfiguration.getReadTimeout());

        config.setHttpClientEnum(springAuroraConfiguration.getHttpClientEnum());

        config.interceptorChain().addInterceptor(applicationContext.getBeansOfType(AuroraInterceptor.class).values());
    }
}