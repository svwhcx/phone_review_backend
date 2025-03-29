package com.svwh.phonereview.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 16:00
 */
@RestController
@RequestMapping("/user/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    /**
     * 刷新Token。
     * @return
     */
    @PostMapping("/refreshToken")
    public String refresAccessToken(){
        return authService.refreshAccessToken();
    }
}
