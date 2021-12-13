package com.demo.token.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName EncryptProperties
 * @Description 配置类信息 实体
 * @Author wujh
 * @Date 2021/5/25 0025 下午 2:46
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "hlms.encrypt")
public class EncryptProperties {

    /**
     * 工作秘钥密文和私钥存储路径
     */
    private String filePath;

    /**
     * 需要运行的子系统标识
     */
    private String suiteId;

    private String enabled;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(String suiteId) {
        this.suiteId = suiteId;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}
