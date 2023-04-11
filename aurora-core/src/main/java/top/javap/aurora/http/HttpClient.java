package top.javap.aurora.http;

import top.javap.aurora.domain.HttpResponse;

import java.util.Map;

/**
 * @Author: pch
 * @Date: 2023/3/23 19:32
 * @Description:
 */
public interface HttpClient {

    HttpResponse get(String url);

    HttpResponse get(String url, Map<String, Object> parameters);

    HttpResponse get(String url, Map<String, Object> headers, Map<String, Object> parameters);
}