package com.demo.token.util;

/**
 * @ClassName Test
 * @Description TODO
 * @Author wujh
 * @Date 2021/5/27 0027 下午 3:44
 * @Version 1.0
 */
public class EncryptService {

    /**
     * 工作秘钥密文和私钥存储地址
     */
    private String filePath;

    /**
     * 子系统标识
     */
    private String suiteId;

    /**
     * 工作秘钥密文和私钥存储地址
     */
    private String enabled;

    public EncryptService() {
    }

    public EncryptService(String filePath, String suiteId, String enabled) {
        this.filePath = filePath;
        this.suiteId = suiteId;
        this.enabled = enabled;
    }

    public void generateSecretFile () throws Exception {
        if(!"false".equals(enabled)) {
            /**
             * 给文件路径和子系统标识（即文件名称）赋值
             */
            EncryptUtil.getInstance().getObject(filePath, suiteId);

            EncryptUtil.getInstance().generateSecretFile();
        }
    }

    /**
     * 对字符串进行加密
     * @param msg 明文
     * @return
     */
    public String encrypt (String msg) {
        String result = null;
        if("false".equals(enabled)) {
            result = msg;
        } else {
            result = RSAKeyUtil.getInstance().encryptString(msg);
        }
        return result;
    }

    /**
     * 对字符串进行解密
     * @param msg 加密字符串
     * @return
     */
    public String decrypt (String msg) {
        String result = null;
        if("false".equals(enabled)) {
            result = msg;
        } else {
            result = RSAKeyUtil.getInstance().decryptString(msg);
        }
        return result;
    }

    /**
     *  签名
     * @param data 待签名数据
     * @return String 签名结果
     */
    public String sign(String data) throws Exception {
        String result = null;
        if("false".equals(enabled)) {
            result = data;
        } else {
            result = RSAKeyUtil.getInstance().sign(data);
        }
        return result;
    }

    /**
     * 验证签名
     * @param srcData 原始字符串
     * @param sign 签名
     * @return boolean 返回验签的结果
     */
    public boolean verifySing(String srcData, String sign) throws Exception {
        boolean flag = true;
        if(!"false".equals(enabled)) {
            flag = RSAKeyUtil.getInstance().verify(srcData, sign);
        }
        return flag;
    }
}
