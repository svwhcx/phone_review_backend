package com.svwh.phonereview.auth.token;

/**
 * @description 认证模块提供的用户信息
 * @Author cxk
 * @Date 2025/3/25 14:00
 */
public class AUser {

    private Long id;

    private String username;

    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public AUser() {
    }

    public AUser(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    @Override
    public String toString() {
        return "AUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
