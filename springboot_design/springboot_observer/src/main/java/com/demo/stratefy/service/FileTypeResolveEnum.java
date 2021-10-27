package com.demo.stratefy.service;

/**
 * @author lkz
 * @date 2021/10/27 9:16
 * @description
 */
public enum FileTypeResolveEnum {

    File_A_RESOLVE("夏天"),
    File_B_RESOLVE("秋天"),
    File_DEFAULT_RESOLVE("春天");

    private String status;

    FileTypeResolveEnum(String status) {
        this.status = status;
    }

    FileTypeResolveEnum() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
