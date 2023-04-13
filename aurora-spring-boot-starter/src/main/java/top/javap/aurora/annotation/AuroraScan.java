package top.javap.aurora.annotation;

import org.springframework.context.annotation.Import;
import top.javap.aurora.spring.AuroraScannerRegistrar;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AuroraScannerRegistrar.class)
public @interface AuroraScan {

    String[] scanPackages() default {};
}