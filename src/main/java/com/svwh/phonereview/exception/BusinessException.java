package com.svwh.phonereview.exception;


public class BusinessException extends RuntimeException{

    /**
     * 错误码
     */
    private final Integer errCode;

    /**
     * 错误提示
     */
    private final String errorMsg;

    private  Integer errHttpCode = 200;

    public BusinessException(IErrorCode iErrorCode)
    {
        super(iErrorCode.msg());
        this.errCode = iErrorCode.code();
        this.errorMsg = iErrorCode.msg();
        this.errHttpCode = iErrorCode.errHttpCode();
    }

    public BusinessException(Integer errCode,String errorMsg){
        super(errorMsg);
        this.errCode = errCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(Integer errHttpCode){
        super("");
        this.errCode = null;
        this.errorMsg = null;
        this.errHttpCode = errHttpCode;
    }

    public BusinessException(String errorMsg){
        super(errorMsg);
        this.errCode = -1;
        this.errorMsg = errorMsg;
    }

    /**
     * 获取错误码
     * @return 当前异常的错误码
     */
    public Integer getErrCode(){
        return this.errCode;
    }

    /**
     * 获取错误信息
     * @return 当前异常的错误信息
     */
    public String getErrorMsg(){
        return this.errorMsg;
    }

    public Integer getErrHttpCode(){
        return this.errHttpCode;
    }

}
