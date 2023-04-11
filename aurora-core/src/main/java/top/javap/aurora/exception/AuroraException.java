package top.javap.aurora.exception;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/7
 **/
public class AuroraException extends RuntimeException {

    public AuroraException(String message) {
        super(message);
    }

    public AuroraException(String message, Throwable cause) {
        super(message, cause);
    }
}