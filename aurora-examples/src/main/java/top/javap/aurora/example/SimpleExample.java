package top.javap.aurora.example;

import com.alibaba.fastjson.JSON;
import top.javap.aurora.Aurora;
import top.javap.aurora.enums.HttpClientEnum;
import top.javap.aurora.example.api.UomgMapper;
import top.javap.aurora.example.result.MusicResult;
import top.javap.aurora.example.result.QingHuaResult;
import top.javap.aurora.exception.AuroraException;
import top.javap.aurora.executor.AuroraFuture;
import top.javap.aurora.executor.Callback;

/**
 * 直接使用 简单示例
 *
 * @author: pch
 * @description:
 * @date: 2023/4/4
 **/
public class SimpleExample {
    public static void main(String[] args) throws Exception {
//        Aurora.config().setCorePoolSize(16);
//        Aurora.config().interceptorChain().addInterceptor(new LogInterceptor());
        Aurora.config().setHttpClientEnum(HttpClientEnum.OKHTTP);
        UomgMapper mapper = Aurora.getInstance(UomgMapper.class);
//        qingHua(mapper);
//        qingHuaOnFuture(mapper);
        qingHuaOnCallback(mapper);
//        music(mapper);
    }

    static void music(UomgMapper mapper) {
        MusicResult result = mapper.randMusic("热歌榜", "json");
        System.err.println("music:" + JSON.toJSONString(result));
    }

    static void qingHua(UomgMapper mapper) {
        QingHuaResult result = mapper.qingHua();
        System.err.println("土味情话:" + JSON.toJSONString(result));
    }

    static void qingHuaOnFuture(UomgMapper mapper) {
        AuroraFuture<QingHuaResult> future = mapper.qingHuaOnFuture();
        System.err.println("future:" + future);
        long time = System.currentTimeMillis();
        System.err.println("result:" + JSON.toJSONString(future.getResult()) + "\n耗时:" + (System.currentTimeMillis() - time) + "ms");
    }

    static void qingHuaOnCallback(UomgMapper mapper) {
        final long time = System.currentTimeMillis();
        mapper.qingHuaOnCallback(new Callback<QingHuaResult>() {
            @Override
            public void onSuccess(QingHuaResult result) {
                System.err.println("callback result:" + JSON.toJSONString(result) + "\n耗时:" + (System.currentTimeMillis() - time) + "ms");
            }

            @Override
            public void onError(AuroraException exception) {
                System.err.println("onError:" + exception.getMessage());
            }
        });
    }
}