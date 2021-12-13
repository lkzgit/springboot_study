package com.demo.token.util;



import com.demo.token.constant.EncryptConstant;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * @ClassName PBKDF2Util
 * @Description 工作秘钥工具类
 * @Author wujh
 * @Date 2021/5/25 0025 下午 8:08
 * @Version 1.0
 */
public class PBKDF2Util {

    /**
     * @auther: Ragty
     * @describe: 通过加密的强随机数生成盐(最后转换为16进制)
     * @return: java.lang.String
     * @date: 2018/11/2
     */
    public static String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance(EncryptConstant.SIGN_ALGORITHMS);
        byte[] salt = new byte[EncryptConstant.SALT_BYTE_SIZE];
        random.nextBytes(salt);

        return toHex(salt);
    }
    

    /**
     * @auther: Ragty
     * @describe: 生成密文
     * @param: [password(明文密码), salt(盐值)]
     * @return: java.lang.String
     * @date: 2018/11/2
     */
    public static String getEncryptedPassword(String password, String salt) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        KeySpec spec = new PBEKeySpec(password.toCharArray(),
                fromHex(salt), EncryptConstant.PBKDF2_ITERATIONS, EncryptConstant.HASH_BIT_SIZE);
        SecretKeyFactory f = SecretKeyFactory.getInstance(EncryptConstant.PBKDF2_ALGORITHM);
        return toHex(f.generateSecret(spec).getEncoded());
    }

    /**
     * @auther: Ragty
     * @describe: 十六进制字符串转二进制字符串
     * @param: [hex]
     * @return: byte[]
     * @date: 2018/11/2
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    /**
     * @auther: Ragty
     * @describe: 二进制字符串转十六进制字符串
     * @param: [array]
     * @return: java.lang.String
     * @date: 2018/11/2
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }

}
