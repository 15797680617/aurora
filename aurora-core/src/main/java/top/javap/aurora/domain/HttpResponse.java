package top.javap.aurora.domain;

/**
 * @author: pch
 * @description:
 * @date: 2023/3/23
 **/
public class HttpResponse {

    private String body;
    private String message;
    private int code;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}