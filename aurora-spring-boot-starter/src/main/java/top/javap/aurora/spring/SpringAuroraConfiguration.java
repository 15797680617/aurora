package top.javap.aurora.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import top.javap.aurora.enums.HttpClientEnum;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/13
 **/
@ConfigurationProperties(prefix = "aurora")
public class SpringAuroraConfiguration {

    private long connectTimeout = 5000L;
    private long writeTimeout = 5000L;
    private long readTimeout = 5000L;

    private int corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
    private int maxPoolSize = 200;
    private int keepAliveSeconds = 60;
    private int queueSize = 1000;

    private HttpClientEnum httpClientEnum = HttpClientEnum.OKHTTP;

    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setWriteTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public void setHttpClientEnum(HttpClientEnum httpClientEnum) {
        this.httpClientEnum = httpClientEnum;
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

    public HttpClientEnum getHttpClientEnum() {
        return httpClientEnum;
    }
}