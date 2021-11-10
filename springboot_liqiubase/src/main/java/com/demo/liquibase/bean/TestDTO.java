package com.demo.liquibase.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lkz
 * @date 2021/11/10 14:01
 * @description
 */
@TableName("hlms_dict")
@Data
public class TestDTO {

    @TableField("dict_name")
    private String dictName;
    @TableField("id")
    private String id;
}
