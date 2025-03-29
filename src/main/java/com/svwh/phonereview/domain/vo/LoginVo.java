package com.svwh.phonereview.domain.vo;

import lombok.Data;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/29 9:31
 */
@Data
public class LoginVo {

    private String token;

    private UserVo user;
}
