package top.javap.aurora.enums;

/**
 * @Author: pch
 * @Date: 2023/4/7 14:32
 * @Description:
 */
public enum HttpMethod {
    GET("get"), POST("post");

    private final String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public static HttpMethod getByName(String name) {
        for (HttpMethod method : values()) {
            if (method.name.equalsIgnoreCase(name)) {
                return method;
            }
        }
        return null;
    }
}