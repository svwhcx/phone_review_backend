package com.svwh.phonereview.handler;

import com.svwh.phonereview.common.ResultVo;
import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.query.PageVo;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {



    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 顶层的业务异常处理器
     * @param e
     * @return 业务异常响应
     */
    @ExceptionHandler(BusinessException.class)
    public ResultVo businessException(BusinessException e, HttpServletResponse response){
        // 若已经定义了HTTP响应码，则无需返回详细的错误信息。
        if (e.getErrHttpCode()!=null){
            response.setStatus(e.getErrHttpCode());
            return new ResultVo(e.getErrCode(),e.getErrorMsg());
        }
        // 获取业务异常错误信息
        LOGGER.info("业务异常日志：{}",e.getMessage());
        return new ResultVo(e.getErrCode(),e.getErrorMsg());
    }

    /**
     * 处理未捕获解决的系统异常
     * @param e
     * @return 系统错误响应
     */
    @ExceptionHandler(Exception.class)
    public ResultVo exception(Exception e,HttpServletResponse response){
        LOGGER.error("================系统发生了错误==========");
        LOGGER.error("发生错误的原因：{}",e.getMessage());
        LOGGER.error("异常链为   ",e.getCause());
        e.printStackTrace();
        response.setStatus(500);
        return new ResultVo(500,"系统错误");
    }
}
