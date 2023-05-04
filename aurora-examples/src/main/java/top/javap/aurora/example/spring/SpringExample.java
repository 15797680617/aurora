package top.javap.aurora.example.spring;

import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import top.javap.aurora.example.api.UomgMapper;
import top.javap.aurora.example.result.QingHuaResult;

/**
 * Spring整合示例
 *
 * @author: pch
 * @description:
 * @date: 2023/5/4
 **/
public class SpringExample {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        UomgMapper mapper = context.getBean(UomgMapper.class);
        QingHuaResult result = mapper.qingHua();
        System.err.println(JSON.toJSONString(result));
    }
}