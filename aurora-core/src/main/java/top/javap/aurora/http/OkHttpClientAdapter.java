package top.javap.aurora.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.javap.aurora.domain.HttpResponse;
import top.javap.aurora.util.Maps;

import java.io.IOException;
import java.util.Map;

/**
 * @author: pch
 * @description:
 * @date: 2023/3/23
 **/
public class OkHttpClientAdapter extends BaseHttpClient {

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public HttpResponse get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get().build();
        try {
            Response response = client.newCall(request).execute();
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.setBody(response.body().string());
            httpResponse.setCode(response.code());
            httpResponse.setMessage(response.message());
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HttpResponse get(String url, Map<String, Object> parameters) {
        url += "?1=1";
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            url += "&" + entry.getKey() + "=" + entry.getValue();
        }
        Request request = new Request.Builder()
                .url(url)
                .get().build();
        try {
            Response response = client.newCall(request).execute();
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.setBody(response.body().string());
            httpResponse.setCode(response.code());
            httpResponse.setMessage(response.message());
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HttpResponse get(String url, Map<String, Object> headers, Map<String, Object> parameters) {
        url += "?1=1";
        if (Maps.notEmpty(parameters)) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                url += "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        Request.Builder builder = new Request.Builder().url(url);
        if (Maps.notEmpty(headers)) {
            headers.entrySet().forEach(e -> builder.header(e.getKey(), e.getValue().toString()));
        }
        Request request = builder.get().build();
        try {
            Response response = client.newCall(request).execute();
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.setBody(response.body().string());
            httpResponse.setCode(response.code());
            httpResponse.setMessage(response.message());
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}