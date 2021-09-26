package com.demo.security.domain.loginVo;

import java.io.Serializable;

/**
 * @author lkz
 * @date 2021/9/26 10:28
 * @description 登录接受类
 */
public class UserLogin implements Serializable {

    private String userName;

    private String password;

    private String code;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
