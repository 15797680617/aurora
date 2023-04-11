package top.javap.aurora.example.api;

import com.alibaba.fastjson.JSONObject;
import top.javap.aurora.annotation.Get;
import top.javap.aurora.annotation.Mapper;
import top.javap.aurora.annotation.Param;
import top.javap.aurora.example.result.WeatherErrorResult;
import top.javap.aurora.example.result.WeatherResult;
import top.javap.aurora.executor.Callback;

/**
 * @Author: pch
 * @Date: 2023/4/4 19:29
 * @Description:
 */
@Mapper
public interface GaoDeMapper {

    @Get("https://restapi.amap.com/v3/weather/weatherInfo")
    WeatherResult queryWeather(@Param("key") String key, @Param("city") int city);

    @Get("https://restapi.amap.com/v3/weather/weatherInfo")
    JSONObject queryWeather1(@Param("key") String key, @Param("city") int city);

    @Get("https://restapi.amap.com/v3/weather/weatherInfo")
    String ss(@Param("key") String key, @Param("city") int city);

    @Get("https://restapi.amap.com/v3/weather/weatherInfo")
    String async(@Param("key") String key, @Param("city") int city, Callback<String> callback);

    @Get("https://restapi.amap.com/v3/weather/weatherInfo")
    WeatherErrorResult abcd(@Param("key") String key, @Param("city") int city);

    @Get("dadwd")
    String aa();

}