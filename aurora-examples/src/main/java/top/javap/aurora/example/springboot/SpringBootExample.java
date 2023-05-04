package top.javap.aurora.example.springboot;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.javap.aurora.annotation.AuroraScan;
import top.javap.aurora.example.api.UomgMapper;
import top.javap.aurora.example.result.QingHuaResult;

/**
 * SpringBoot整合示例
 *
 * @author: pch
 * @description:
 * @date: 2023/4/12
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootExample.class)

@SpringBootApplication(scanBasePackages = "top.javap.aurora.example.springboot")
@AuroraScan(scanPackages = "top.javap.aurora.example")
public class SpringBootExample {

    @Autowired
    UomgMapper uomgMapper;

    @Test
    public void test() {
        QingHuaResult result = uomgMapper.qingHua();
        System.err.println(JSON.toJSONString(result));
    }
}