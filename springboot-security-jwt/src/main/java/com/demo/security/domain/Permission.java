package com.demo.security.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lkz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer permissionId;

    private String permissionName;

    private String permissionValue;

    private String permissionType;

    private String permissionState;

    private String superiorId;

    private String createTime;

    private String updateTime;
}
