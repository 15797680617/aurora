package top.javap.aurora.example.api;

import top.javap.aurora.annotation.Get;
import top.javap.aurora.annotation.Mapper;
import top.javap.aurora.annotation.Param;
import top.javap.aurora.example.result.WeatherErrorResult;
import top.javap.aurora.executor.AuroraFuture;
import top.javap.aurora.executor.Callback;

/**
 * @Author: pch
 * @Date: 2023/4/4 19:29
 * @Description:
 */
@Mapper(baseUrl = "https://restapi.amap.com/v3")
public interface GaoDeMapper {

    @Get("/weather/weatherInfo")
    WeatherErrorResult sync(@Param("key") String key, @Param("city") int city);

    @Get("/weather/weatherInfo")
    AuroraFuture<WeatherErrorResult> future(@Param("key") String key, @Param("city") int city);

    @Get("/weather/weatherInfo")
    void callback(@Param("key") String key, @Param("city") int city, Callback<WeatherErrorResult> cb);

    default String defalutMethod() {
        System.err.println("invoke default...");
        return "abc";
    }
}