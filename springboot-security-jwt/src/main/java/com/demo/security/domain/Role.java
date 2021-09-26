package com.demo.security.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author lkz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer roleId;

    private String roleName;

    private String roleTitle;

    private String state;

    private String description;

    private String createTime;

    private String updateTime;

    private List<Permission> permissionList;
}
