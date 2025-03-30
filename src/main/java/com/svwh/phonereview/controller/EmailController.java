package com.svwh.phonereview.controller;

import com.svwh.phonereview.auth.annotation.IgnoreAuth;
import com.svwh.phonereview.verifycode.IVerifyCode;
import com.svwh.phonereview.verifycode.VerifyCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/30 15:09
 */
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final IVerifyCode iVerifyCode;

    /**
     * 用户发送一个邮箱验证码
     * @param verifyCodeRequest 目标邮箱
     */
    @PostMapping("/verifyCode/send")
    @IgnoreAuth
    public void sendVerifyCode(@RequestBody VerifyCodeRequest verifyCodeRequest) {
        iVerifyCode.generateVerifyCode(verifyCodeRequest);
    }

    /**
     * 用户校验一个验证码是否正确
     * @param verifyCodeRequest 验证码是否正确
     */
    @PostMapping("/verifyCode/check")
    @IgnoreAuth
    public Boolean checkVerifyCode(@RequestBody VerifyCodeRequest verifyCodeRequest) {
        return iVerifyCode.checkVerifyCode(verifyCodeRequest);
    }
}
