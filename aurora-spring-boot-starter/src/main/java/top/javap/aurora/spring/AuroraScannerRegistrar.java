package top.javap.aurora.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import top.javap.aurora.annotation.AuroraScan;
import top.javap.aurora.util.Assert;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/12
 **/
public class AuroraScannerRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String[] scanPackages = importingClassMetadata.getAnnotations().get(AuroraScan.class).getStringArray("scanPackages");
        Assert.notEmpty(scanPackages, "scanPackages can not be empty");
        ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
        scanner.registerFilters();
        scanner.doScan(scanPackages);

//        registry.registerBeanDefinition("SpringAuroraInitializer", BeanDefinitionBuilder.genericBeanDefinition(SpringAuroraInitializer.class).getBeanDefinition());
    }
}