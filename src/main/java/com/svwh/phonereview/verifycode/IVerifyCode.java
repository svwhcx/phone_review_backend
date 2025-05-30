package com.svwh.phonereview.verifycode;


/**
 * @description 这里是验证码服务<p>
 * 提供的验证码服务包括，图片验证码、手机验证码、邮箱验证码
 * @Author cxk
 * @Date 2025/2/27 9:26
 */
public interface IVerifyCode {

    /**
     * 发送验证码操作。
     *
     * @param verifyCodeRequest 发送验证码请求
     */
//    void sendVerifyCode(VerifyCodeRequest verifyCodeRequest);

    /**
     * 校验校验码是否正确
     *
     * @param verifyCodeRequest 校验验证码请求
     * @return 验证码是否正确
     */
    Boolean checkVerifyCode(VerifyCodeRequest verifyCodeRequest);

    /**
     * 标记该验证码已被使用
     *
     * @param verifyCodeRequest 验证码信息
     */
    void verifyCodeUsed(VerifyCodeRequest verifyCodeRequest);

    /**
     * 生成/发送一个验证码的操作
     * <p>
     * @param verifyCodeRequest 验证码请求
     *
     * @return 包含本次验证的一些需求信息
     */
    void generateVerifyCode(VerifyCodeRequest verifyCodeRequest) ;
}
