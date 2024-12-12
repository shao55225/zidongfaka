package com.payment.exception;

/**
 * @Author: Chand
 * @Date: 13-12-04
 * @Time: 下午2:30
 * @Description: 异常抛出
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = -238091758285157331L;

    private String code;
    private String msg;

    public ServiceException() {
        super();
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.code = errCode;
        this.msg = errMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
