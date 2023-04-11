package top.javap.aurora.example.result;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/11
 **/
public class WeatherErrorResult {

    private int status;
    private String info;
    private int infocode;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getInfocode() {
        return infocode;
    }

    public void setInfocode(int infocode) {
        this.infocode = infocode;
    }
}