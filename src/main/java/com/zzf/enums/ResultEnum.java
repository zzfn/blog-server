package com.zzf.enums;

/**
 * @author cach1e
 */
public enum ResultEnum {
    /**
     * 未知异常
     */
    UNKNOWN_ERROR(4000, "未知异常"),
    SUCCESS(0, "success"),
    ACCESS_DENIED(4100, "无效用户");
    private final Integer code;
    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
