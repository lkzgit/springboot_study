package com.demo.token.constant;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName EncryptConstant.java
 * @Description TODO
 * @createTime 2021年12月12日 00:22:00
 */
public class EncryptConstant {

    /**
     * 编码格式
     */
    public static final String ENCODING = "UTF-8";

    /**
     * AES的长度
     */
    public static final int AES_KEYSIZE = 128;

    /**
     * 加密算法
     */
    public static final String AES_KEY_ALGORITHM = "AES";

    /**
     * 加密算法
     */
    public static final String RSA_KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA1PRNG";

    /**
     * RSA加密长度
     */
    public static final int RSA_KEYSIZE = 2048;

    /**
     * rsa公钥的module值
     */
    public static final int PUBLIC_KEY_MODULE = 65537;

    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    /**
     * 盐的长度
     */
    public static final int SALT_BYTE_SIZE = 32 / 2;

    /**
     * 生成密文的长度
     */
    public static final int HASH_BIT_SIZE = 128;

    /**
     * 迭代次数
     */
    public static final int PBKDF2_ITERATIONS = 1000;

    /**
     * 验签算法
     */
    public static final String SIGN_KEY = "MD5withRSA";
}
