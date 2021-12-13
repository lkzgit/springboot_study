package com.demo.token.util;


import com.demo.token.constant.EncryptConstant;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RSAKeyUtil
 * @Description 密钥对生成工具类和加解密工具类
 * @Author wujh
 * @Date 2021/5/25 0025 下午 8:15
 * @Version 1.0
 */
public class RSAKeyUtil {

    private static RSAKeyUtil instance = null;

    /**
     * 通过构造方法进行初始化
     */
    private RSAKeyUtil() {
    }

    public static RSAKeyUtil getInstance() {
        if (null == instance) {
            instance = new RSAKeyUtil();
        }
        return instance;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptUtil.class);

    /**
     * 用于封装随机产生的公钥与私钥
     */
    static Map<Integer, String> keyMap = new HashMap<Integer, String>();

    private static byte[] wrappedKey;

    /**
     * 生成RSA密钥对
     * @param savePath
     * @throws Exception
     */
    public static void generateRsaKey(String savePath) throws Exception {

        /**
         * 私钥对象
         */
        RSAPrivateKey privateKey;

        /**
         * 公钥对象
         */
        RSAPublicKey publicKey;

        /**
         * 私钥字符串
         */
        String privateKeyString = null;
        /**
         * 公钥字符串
         */
        String publicKeyString;
        try {

            File file = new File(savePath);
            if (file.exists()) {
                String priEndecoded = readKeyUtil(file);
                privateKey = getPrivateKey(priEndecoded);
                publicKey = getPublicFromPrivate(privateKey);

                getKeyMap(privateKey, publicKey);
                getWrappedKey(savePath);
            } else {
                KeyPairGenerator keygen = KeyPairGenerator.getInstance(EncryptConstant.RSA_KEY_ALGORITHM);
                SecureRandom random = new SecureRandom();
                keygen.initialize(EncryptConstant.RSA_KEYSIZE, random);
                KeyPair keyPair = keygen.generateKeyPair();
                publicKey = (RSAPublicKey) keyPair.getPublic();
                privateKey = (RSAPrivateKey) keyPair.getPrivate();

                /**
                 * 获取私钥字符串
                 */
                privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());

                /**
                 * 硬盘存储私钥信息文件
                 */
                createPrbFile(privateKeyString, savePath);
            }

            /**
             * 获取私钥字符串
             */
            privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());

            /**
             * 获取公钥字符串
             */
            publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());

            keyMap.put(0, privateKeyString);
            keyMap.put(1, publicKeyString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对字符串进行加密，并存储在文件中
     * @param wrokSecret
     * @param saveFileName
     * @throws Exception
     */
    public static void encryptByRsa(String wrokSecret, String saveFileName) throws Exception {
        try {

            String pubEndecoded = keyMap.get(1);
            SecretKey  key = AESUtil.getInstance().genSecretKey(Base64.decodeBase64(pubEndecoded));
            RSAPublicKey publicKey = getPublicKey(pubEndecoded);

            Cipher cipher = Cipher.getInstance(EncryptConstant.RSA_KEY_ALGORITHM);
            cipher.init(Cipher.WRAP_MODE, publicKey);

            if (null == wrappedKey || wrappedKey.length == 0) {
                wrappedKey = cipher.wrap(key);
            }
            DataOutputStream out = new DataOutputStream(new FileOutputStream(saveFileName));
            out.writeInt(wrappedKey.length);
            out.write(wrappedKey);
            cipher = Cipher.getInstance(EncryptConstant.AES_KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            crypt(wrokSecret, out, cipher);
            out.close();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对字符串进行加密
     * @param msg 需要加密的字符串
     * @return String 返回加密之后的字符串
     */
    public static String encryptString (String msg) {
        String encryMsg = null;

        try {
            String pubEndecoded = keyMap.get(1);
            SecretKey  key = AESUtil.getInstance().genSecretKey(Base64.decodeBase64(pubEndecoded));
            RSAPublicKey publicKey = getPublicKey(pubEndecoded);

            Cipher cipher = Cipher.getInstance(EncryptConstant.RSA_KEY_ALGORITHM);
            cipher.init(Cipher.WRAP_MODE, publicKey);
            if(null == wrappedKey || wrappedKey.length == 0) {
                wrappedKey = cipher.wrap(key);
            }
            cipher = Cipher.getInstance(EncryptConstant.AES_KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] inBytes = msg.getBytes("UTF-8");
            byte[] outBytes = cipher.doFinal(inBytes);

            encryMsg = Base64.encodeBase64String(outBytes);
            LOGGER.info("加密后字符串值，{} ", encryMsg);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryMsg;
    }

    /**
     * RSA算法对文件解密
     * @param fileName 需要解密的文件
     * @return
     * @throws Exception
     */
    public static String decryptFileByRsa(String fileName) throws Exception {
        String decryptedString = null;

        try {
            DataInputStream in = new DataInputStream(new FileInputStream(fileName));

            if(null == wrappedKey || wrappedKey.length == 0) {
                byte[] wrappedKey = new byte[in.available()];
                int tempbyte;
                while ((tempbyte = in.read(wrappedKey, 0, in.available())) != -1) {
                    in.read(wrappedKey, 0, in.available());
                }
            }

            String priEndecoded = keyMap.get(0);
            RSAPrivateKey privatek = getPrivateKey(priEndecoded);
            RSAPublicKey publicKey = getPublicFromPrivate(privatek);

            /**
             * 获取公钥字符串
             */
            String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
            keyMap.put(1, publicKeyString);

            Cipher cipher = Cipher.getInstance(EncryptConstant.RSA_KEY_ALGORITHM);
            cipher.init(Cipher.UNWRAP_MODE, privatek);
            Key key = cipher.unwrap(wrappedKey, EncryptConstant.AES_KEY_ALGORITHM, Cipher.SECRET_KEY);

            cipher = Cipher.getInstance(EncryptConstant.AES_KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE , key);

            byte[] outBytes = cryptFile(in, cipher);
            decryptedString = new String(outBytes, EncryptConstant.ENCODING);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("解密后工作秘钥明文，{} ", decryptedString);
        return decryptedString;
    }

    /**
     * 对加密字符串进行解密
     * @param secretMsg 加密字符串
     * @return String 返回解密之后的字符串
     */
    public static String decryptString (String secretMsg) {
        String decryptedString = null;

        try {

            byte[] secret = Base64.decodeBase64(secretMsg);

            String priEndecoded = keyMap.get(0);
            RSAPrivateKey privatek = getPrivateKey(priEndecoded);

            Cipher cipher = Cipher.getInstance(EncryptConstant.RSA_KEY_ALGORITHM);
            cipher.init(Cipher.UNWRAP_MODE, privatek);
            Key key = cipher.unwrap(wrappedKey, EncryptConstant.AES_KEY_ALGORITHM, Cipher.SECRET_KEY);
            cipher = Cipher.getInstance(EncryptConstant.AES_KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE , key);

            byte[] outBytes = cipher.doFinal(secret);
            decryptedString = new String(outBytes, EncryptConstant.ENCODING);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return decryptedString;
    }

    /**
     * 获取私钥
     * @param endecoded 根据私钥字符串，获取私钥信息
     * @return
     */
    public static RSAPrivateKey getPrivateKey(String endecoded) {
        try {

            byte[] decoded = Base64.decodeBase64(endecoded);
            KeyFactory keyFactory = KeyFactory.getInstance(EncryptConstant.RSA_KEY_ALGORITHM);
            RSAPrivateKey privateKey = (RSAPrivateKey)keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decoded));
            return privateKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取公钥
     * @param endecoded 根据私钥字符串，获取公钥信息
     * @return
     */
    public static RSAPublicKey getPublicKey(String endecoded) {
        try {

            byte[] decoded = Base64.decodeBase64(endecoded);
            KeyFactory keyFactory = KeyFactory.getInstance(EncryptConstant.RSA_KEY_ALGORITHM);
            RSAPublicKey publicKey = (RSAPublicKey)keyFactory.generatePublic(new X509EncodedKeySpec(decoded));

            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取解密的warappedKey
     * @param filePath
     * @return
     */
    public static void getWrappedKey(String filePath) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(filePath));
            byte[] wrappedKey = new byte[in.available()];

            int tempbyte;
            while ((tempbyte = in.read(wrappedKey, 0, in.available())) != -1) {
                in.read(wrappedKey, 0, in.available());
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 根据私钥信息获取公钥信息，用于解密
     * @param privateKey 私钥信息
     * @return RSAPublicKey 返回公钥信息
     */
    public static RSAPublicKey getPublicFromPrivate(RSAPrivateKey privateKey) {
        try {
            BigInteger modulus = privateKey.getModulus();

            KeyFactory keyFactory = KeyFactory.getInstance(EncryptConstant.RSA_KEY_ALGORITHM);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus,
                    new BigInteger(String.valueOf(EncryptConstant.PUBLIC_KEY_MODULE)));
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);

            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将私钥存储到规定文件中
     * @param priKey 私钥内容
     * @param savePath 存储文件路径
     */
    public static void createPrbFile(String priKey, String savePath) {
        try {
            /**
             * 先建立一个可以向文件中写入数据的输出流对象，这一步的时候要给出写入文件的路径；
             */
            OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(savePath, true),"UTF-8");

            /**
             * 调用fw的write方法，向文件中写入数据；
             * 此时的数据实际是写到了临时文件缓冲区中，并没有写到你指定的文件中去
             */
            osw.write(priKey);

            /**
             * 调用FileWriter对象的flush()方法，将数据写入到指定文件中
             */
            osw.flush();

            /**
             * 调用close方法，关闭输出流，这个方法会自动调用上一步的flush方法，将缓冲区的数据写到目的文件中去
             */
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件内容
     * @param file
     * @return
     */
    public static String readKeyUtil(File file){
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), EncryptConstant.ENCODING));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    /**
     * 对数据块加密
     * @param in
     * @param cipher
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static byte[] cryptFile(InputStream in, Cipher cipher) throws IOException, GeneralSecurityException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int blockSize = cipher.getBlockSize();
        int outputSize = cipher.getOutputSize(blockSize);
        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];

        int inLength = 0;
        boolean next = true;
        while (next) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                output.write(outBytes, 0, outLength);
            } else {
                next = false;
            }
        }
        if (inLength > 0) {
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        } else {
            outBytes = cipher.doFinal();
        }
        output.write(outBytes);

        return output.toByteArray();
    }

    /**
     * 对字符串进行加解密
     * @param workSecret 需要加解密的字符串
     * @param out 输出文件
     * @param cipher
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static void crypt(String workSecret, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {
        byte[] inBytes = workSecret.getBytes(EncryptConstant.ENCODING);
        byte[] outBytes = cipher.doFinal(inBytes);
        out.write(outBytes);
    }

    /**
     * 生成keyMap
     * @param privateKey 私钥信息
     * @param publicKey 公钥信息
     */
    public static void getKeyMap (RSAPrivateKey privateKey, RSAPublicKey publicKey) {
        /**
         * 获取私钥字符串
         */
        String privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());

        /**
         * 获取公钥字符串
         */
        String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());

        keyMap.put(0, privateKeyString);
        keyMap.put(1, publicKeyString);
    }

    /**
     * 签名
     * @param data 待签名数据
     * @return 签名
     * @throws Exception
     */
     public static String sign(String data) throws Exception {

         // 获取私钥
         PrivateKey key = getPrivateKey(keyMap.get(0));

         Signature signature = Signature.getInstance(EncryptConstant.SIGN_KEY);
         signature.initSign(key);
         signature.update(data.getBytes());
         return new String(Base64.encodeBase64(signature.sign()));
     }

    /**
     * 验签
     * @param srcData 原始字符串
     * @param sign 签名
     * @return 是否验签通过
     * @throws Exception
     */
    public static boolean verify(String srcData, String sign) throws Exception {

        // 获取公钥
        RSAPublicKey publicKey = getPublicKey(keyMap.get(1));

        Signature signature = Signature.getInstance(EncryptConstant.SIGN_KEY);
        signature.initVerify(publicKey);
        signature.update(srcData.getBytes());

        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }
}
