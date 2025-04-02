package com.svwh.phonereview.exception;


public interface IErrorCode {

    /**
     * @return 异常的状态码
     */
    Integer code();

    /**
     * @return 异常状态的信息
     */
    String msg();

    /**
     * 异常状态的时候，是否需要直接修改http响应<p>
     * 默认直接返回200.
     * @return 要修改的http响应码
     */
    default Integer errHttpCode(){
        return 200;
    }
}
