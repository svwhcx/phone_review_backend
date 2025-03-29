package com.svwh.phonereview.auth.token;

import lombok.Data;
import lombok.ToString;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 17:49
 */
@ToString
@Data
public class TokenInfo {


    private Long userId;

    private String role;

    private String username;

    enum RoleEnum{

        user("user"),
        admin("admin");

        private String role;

        RoleEnum(){}

        RoleEnum(String role){
            this.role = role;
        }

        public String getRole(){
            return this.role;
        }

    }

    public static class Builder{

        TokenInfo tokenInfo = new TokenInfo();


        public Builder role(String role){
            tokenInfo.setRole(role);
            return this;
        }

        public Builder userId(Long userId){
            tokenInfo.setUserId(userId);
            return this;
        }

        public Builder username(String username){
            tokenInfo.setUsername(username);
            return this;
        }

        public TokenInfo build(){
            return tokenInfo;
        }
    }

}
