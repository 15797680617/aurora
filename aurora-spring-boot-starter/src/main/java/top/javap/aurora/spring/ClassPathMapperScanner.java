package top.javap.aurora.spring;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.CollectionUtils;
import top.javap.aurora.annotation.Mapper;

import java.io.IOException;
import java.util.Set;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/12
 **/
public class ClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

    public ClassPathMapperScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public void registerFilters() {
        addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return metadataReader.getAnnotationMetadata().hasAnnotation(Mapper.class.getName());
            }
        });
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().hasAnnotation(Mapper.class.getName());
    }

    public Set<BeanDefinitionHolder> doScan(String[] basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        if (!CollectionUtils.isEmpty(beanDefinitionHolders)) {
            processBeanDefinitions(beanDefinitionHolders);
        }
        return beanDefinitionHolders;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        beanDefinitionHolders.forEach(this::processBeanDefinition);
    }

    private void processBeanDefinition(BeanDefinitionHolder beanDefinitionHolder) {
        BeanDefinition beanDefinition = beanDefinitionHolder.getBeanDefinition();
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
        beanDefinition.setBeanClassName(MapperFactoryBean.class.getName());
    }
}