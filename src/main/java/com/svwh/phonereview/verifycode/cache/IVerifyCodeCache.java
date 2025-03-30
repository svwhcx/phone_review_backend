package com.svwh.phonereview.verifycode.cache;


import com.svwh.phonereview.verifycode.VerifyCodeRequest;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/8 11:55
 */
public interface IVerifyCodeCache {


    /**
     * 保存验证码的数据<p>
     * 包括：key：手机号/邮箱账号/图片验证码的UUID<p>
     *     value: 对应的需要验证的编码数据
     * @param verifyCodeRequest 验证码的数据
     */
    void saveCode(VerifyCodeRequest verifyCodeRequest);

    /**
     * 校验一个验证码是否正确
     * @param verifyCodeRequest 验证码数据
     * @return
     */
    boolean checkCode(VerifyCodeRequest verifyCodeRequest);


    /**
     * 校验一个验证码通过后，移除对应的验证码数据
     * @param verifyCodeRequest 验证码数据
     * @param isRemoveCode 是否移除
     * @return
     */
    boolean checkCode(VerifyCodeRequest verifyCodeRequest, boolean isRemoveCode);


    /**
     * 标记一个验证码已经被使用/已经过期了<p>
     * 主要是通过addr来进行标记的
     * @param verifyCodeRequest 验证码的一些数据
     */
    void setCodeUsed(VerifyCodeRequest verifyCodeRequest);



}
