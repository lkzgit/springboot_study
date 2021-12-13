package com.demo.consumer.entity;

import com.demo.consumer.annotation.NeedEncryptField;
import lombok.Data;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName User.java
 * @Description TODO
 * @createTime 2021年12月08日 22:08:00
 */
@Data
public class User {

    private Integer id;

    private String name;
    @NeedEncryptField
    private String phone;

    private String adress;

}
