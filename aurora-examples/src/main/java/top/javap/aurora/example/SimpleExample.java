package top.javap.aurora.example;

import com.alibaba.fastjson.JSON;
import top.javap.aurora.Aurora;
import top.javap.aurora.example.api.GaoDeMapper;
import top.javap.aurora.example.result.WeatherErrorResult;
import top.javap.aurora.exception.AuroraException;
import top.javap.aurora.executor.AuroraFuture;
import top.javap.aurora.executor.Callback;
import top.javap.aurora.interceptor.LogInterceptor;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/4
 **/
public class SimpleExample {
    public static void main(String[] args) throws Exception {
        Aurora.config().setCorePoolSize(16);
        Aurora.config().interceptorChain().addInterceptor(new LogInterceptor());
        GaoDeMapper mapper = Aurora.getInstance(GaoDeMapper.class);
//        sync(mapper);
//        future(mapper);
//        callback(mapper);
    }

    static void callback(GaoDeMapper mapper) {
        mapper.callback("your key", 123456, new Callback<WeatherErrorResult>() {
            @Override
            public void onSuccess(WeatherErrorResult result) {
                System.err.println("cb:" + JSON.toJSONString(result));
            }

            @Override
            public void onError(AuroraException exception) {
                exception.printStackTrace();
            }
        });
    }

    static void future(GaoDeMapper mapper) {
        AuroraFuture<WeatherErrorResult> future = mapper.future("your key", 123456);
        System.err.println("future:" + future);
        System.err.println("futureResult:" + JSON.toJSONString(future.getResult()));
    }

    static void sync(GaoDeMapper mapper) {
        WeatherErrorResult result = mapper.sync("your key", 123456);
        System.err.println("sync:" + JSON.toJSONString(result));
    }
}