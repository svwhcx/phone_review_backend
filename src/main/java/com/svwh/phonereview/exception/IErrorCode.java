package com.svwh.phonereview.exception;

/**
 * @description 异常状态码顶层接口
 * @author cxk
 * @Date 2025/3/14 14:43
 */
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
