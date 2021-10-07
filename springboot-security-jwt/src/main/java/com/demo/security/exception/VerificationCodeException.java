package com.demo.security.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 */

public class VerificationCodeException extends AuthenticationException {

    public VerificationCodeException() {
        super("图形验证码校验失败");
    }


}
