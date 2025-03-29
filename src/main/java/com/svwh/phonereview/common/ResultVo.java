package com.svwh.phonereview.common;

import java.io.Serializable;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/14 14:36
 */
public class ResultVo<T> implements Serializable {

    private Integer code;

    private T data;

    private String message;

    public ResultVo(T data, String message) {
        this.code = 200;
        this.data = data;
        this.message = message;
    }

    public ResultVo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultVo(T data) {
        this.data = data;
        this.code = 200;
        this.message = "success";
    }

    public ResultVo(Integer code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }
}
