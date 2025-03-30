package com.svwh.phonereview.verifycode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/30 15:03
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeRequest {

    // 发送验证码的目标地址（可以是邮箱、可以是手机号、可以是其他的第三方服务等等）
    private String addr;

    // 验证码（验证时需要）
    private String code;

    // 验证码类型（1：邮箱验证码，2：手机验证码,3:图片验证码）
    private Integer type = 1;

    // 提示是在找回密码还是说是注册操作
    private String msg;

}
