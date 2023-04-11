package top.javap.aurora.config;

import top.javap.aurora.convert.ConverterFactory;
import top.javap.aurora.enums.HttpClientEnum;
import top.javap.aurora.executor.HttpExecutor;
import top.javap.aurora.executor.HttpExecutorFactory;
import top.javap.aurora.handler.DefaultResultHandler;
import top.javap.aurora.handler.ResultHandler;
import top.javap.aurora.reflection.AuroraMethodParser;
import top.javap.aurora.reflection.DefaultAuroraMethodParser;

/**
 * @author: pch
 * @description: 全局配置类
 * @date: 2023/4/10
 **/
public final class AuroraConfiguration {

    /********线程池相关**********/
    private int corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
    private int maxPoolSize = 200;
    private int keepAliveSeconds = 60;
    private int queueSize = 1000;

    /********http相关**********/
    private long connectTimeout = 5000L;
    private long writeTimeout = 5000L;
    private long readTimeout = 5000L;


    private HttpClientEnum httpClientEnum = HttpClientEnum.OKHTTP;
    private AuroraMethodParser auroraMethodParser = new DefaultAuroraMethodParser();
    private ConverterFactory converterFactory = new ConverterFactory();
    private ResultHandler resultHandler = new DefaultResultHandler(this.getConverterFactory());

    public HttpClientEnum getHttpClientEnum() {
        return httpClientEnum;
    }

    public void setHttpClientEnum(HttpClientEnum httpClientEnum) {
        this.httpClientEnum = httpClientEnum;
    }

    public AuroraMethodParser getAuroraMethodParser() {
        return auroraMethodParser;
    }

    public void setHttpMethodParser(AuroraMethodParser auroraMethodParser) {
        this.auroraMethodParser = auroraMethodParser;
    }

    public ResultHandler getResultHandler() {
        return resultHandler;
    }

    public ConverterFactory getConverterFactory() {
        return converterFactory;
    }

    public HttpExecutor getHttpExecutor() {
        return HttpExecutorFactory.getHttpExecutor(httpClientEnum, this);
    }

    public static AuroraConfiguration configuration() {
        return new AuroraConfiguration();
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }
}