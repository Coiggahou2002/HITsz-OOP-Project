package com.coiggahou.AircraftWar.entity;

/**
 * @author coiggahou
 */
public enum ApiResponseStatus {

    /**
     * 业务请求正常处理
     */
    SUCCESS(10000, "success"),

    /**
     * 资源未找到
     */
    RESOURCE_NOT_FOUND(10002, "resource not found"),

    /**
     * 服务端错误
     */
    SERVER_ERROR(10005, "server error"),

    REQUEST_NOT_FOUND(10006, "request not found"),

    /**
     * 认证异常
     */
    AUTHENTICATION_ERROR(10001, "authentication error"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(20001, "user not exist"),

    /**
     * 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(20002, "username or password error");




    /**
     * 状态码
     */
    private int code;

    /**
     * 状态描述信息
     */
    private String msg;

    ApiResponseStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
