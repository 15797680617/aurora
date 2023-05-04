package top.javap.aurora.example.api;

import top.javap.aurora.annotation.Get;
import top.javap.aurora.annotation.Mapper;
import top.javap.aurora.annotation.Param;
import top.javap.aurora.example.result.MusicResult;
import top.javap.aurora.example.result.QingHuaResult;
import top.javap.aurora.executor.AuroraFuture;
import top.javap.aurora.executor.Callback;

/**
 * 教书先生API
 *
 * @link: https://api.oioweb.cn/
 * @Author: pch
 * @Date: 2023/5/4 15:33
 * @Description:
 */
@Mapper(baseUrl = "https://api.uomg.com/api/")
public interface UomgMapper {

    @Get("rand.qinghua")
    QingHuaResult qingHua();

    @Get("rand.qinghua")
    AuroraFuture<QingHuaResult> qingHuaOnFuture();

    @Get("rand.qinghua")
    void qingHuaOnCallback(Callback<QingHuaResult> callback);

    @Get("rand.music")
    MusicResult randMusic(@Param("sort") String sort, @Param("format") String format);
}