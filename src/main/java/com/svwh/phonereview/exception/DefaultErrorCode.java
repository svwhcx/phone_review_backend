package com.svwh.phonereview.exception;

/**
 * @description 提供的常见的默认的状态码
 * @Author cxk
 * @Date 2025/3/14 16:56
 */
public enum DefaultErrorCode implements IErrorCode{


    SYSTEM_INTERNAL_ERRO(500),
    PARAMETER_ERROR(400),
    INVALIDE_TOKEN(401),
    AUTH_FAIL(401),
    SYSTEM_ERROR(500,500,"服务器发生了错误！"),
    PARAM_WRONG(1001,"参数错误"),
    USER_NOT_LOGIN(1002,"当前用户未登录"),
    NO_PERMISSION(1003,"无权限操作"),
    USERNAME_EXIST(1004,"用户名已被占用"),
    PASSWORD_NOT_STRONG(1005,"密码强度不够"),
    EMAIL_EXITS(1006,"该邮箱已注册"),
    EMAIL_NOT_EXIST(1007,"该邮箱未注册"),
    VERIFICATION_ERROR(1008,"验证码错误"),
    LOGIN_FAIL(1009,"账号或密码错误"),
    TOKEN_NOT_EXIST(1010,"非法用户"),
    ILLEGAL_OPERATION(1011,"禁止非法操作"),
    INVALID_IMAGE_TYPE(1012, "文件格式错误" ),
    OLD_PASSWORD_ERROR(1013, "旧密码错误"),
    VERIFY_CODE_EXPIRED(1014,"验证码已过期"),
    FILE_UPLOAD_FAIL(1015,"文件上传失败"),
    FILE_TOO_LARGE(1016,"文件超出大小限制"),
    VERIFYCODE_GENR_FAIL(1017,"验证码生成失败"),
    NOT_SUPPORT_VERIFY_TYPE(1018,"不支持的验证码类型"),
    ACCESS_TOKEN_REFRESH_FAIL(1018,"无效的refreshToken"),
    USER_DISABLED(1019, "当前用户已被限制登录"),
    NOTIFICATION_PUBLISHED(1020,"已发布的公告不可修改" ),
    EXIST_FAVORITE_POST(1021, "您已收藏过该评测"),
    EXIST_LIKE_POST(1022, "您已点赞过该评测"),
    EXIST_LIKE_COMMENT(1023, "您已点赞过该评论"),
    USER_IS_MUTED(1024, "您已被禁言"),
    IMAGE_TOO_LARGE(1025,"图片不得超过5M")
    ;


    /**
     * 返回的错误状态码
     */
    private Integer code;

    /**
     * 错误的状态码的描述信息
     */
    private String msg;

    /**
     * 返回给Response的响应码（直接修改浏览器的响应码，不会有任何描述信息）
     */
    private Integer errHttpCode = 200;

    DefaultErrorCode(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
    DefaultErrorCode(Integer errHttpCode,Integer code,String msg){
        this.errHttpCode = errHttpCode;
        this.code = code;
        this.msg = msg;
    }

    DefaultErrorCode(Integer errHttpCode){
        this.errHttpCode = errHttpCode;
    }

    @Override
    public Integer code() {
        return this.code;
    }

    @Override
    public String msg() {
        return this.msg;
    }

    @Override
    public Integer errHttpCode() {
        return this.errHttpCode;
    }
}
