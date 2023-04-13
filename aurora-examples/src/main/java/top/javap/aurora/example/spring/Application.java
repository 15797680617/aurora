package top.javap.aurora.example.spring;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.javap.aurora.annotation.AuroraScan;
import top.javap.aurora.example.api.GaoDeMapper;
import top.javap.aurora.example.result.WeatherErrorResult;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/12
 **/
@RunWith(SpringRunner.class)
@SpringBootApplication(scanBasePackages = "top.javap.aurora.example.spring")
@SpringBootTest(classes = Application.class)
@AuroraScan(scanPackages = "top.javap.aurora.example")
public class Application {

    @Autowired
    GaoDeMapper gaoDeMapper;

    @Test
    public void test() {
        WeatherErrorResult result = gaoDeMapper.sync("abcd", 123456);
        System.err.println("result:" + JSON.toJSONString(result));
    }
}