package com.demo.security.enumeration;

/**
 * 异常枚举枚举类
 *
 * @author lkz
 */
public enum ErrorCodeEnum {
    E0701("E0701", "用户名不存在!"),
    E0702("E0702", "密码错误!"),
    E0703("E0703", "用户冻结!"),
    E0704("E0704", "响应失败!"),
    E0705("E0705", "未登录或者用户token无效或已过期!"),
    E0706("E0706", "无对应资源权限!"),
    E0708("E0708", "验证码错误!"),
    ;
    private String key;
    private String value;

    ErrorCodeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

}