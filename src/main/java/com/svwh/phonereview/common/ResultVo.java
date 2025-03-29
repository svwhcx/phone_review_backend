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

    private String msg;

    public ResultVo(T data, String msg) {
        this.code = 200;
        this.data = data;
        this.msg = msg;
    }

    public ResultVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVo(T data) {
        this.data = data;
        this.code = 200;
        this.msg = "success";
    }

    public ResultVo(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
