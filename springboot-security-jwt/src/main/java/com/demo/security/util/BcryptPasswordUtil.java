package com.demo.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *    注册时生成密码工具类
 * @author: lkz
 */
public class BcryptPasswordUtil {
    public static String createBCryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
