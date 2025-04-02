package com.svwh.phonereview.permission;


public enum RoleEnum {


    ADMIN(1,"admin"),

    USER(2,"user"),

    ;

    private Integer code;
    private String desc;

    RoleEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
