package com.demo.security.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @Description: User
 * @Author: lkz
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer userId;

    private String account;

    private String userName;

    private String password;

    private String phone;

    private String email;

    private Integer sex;

    private Integer idCard;

    private Integer state;

    private String createTime;

    private String updateTime;

    private List<Role> roleList;

    Set<GrantedAuthority> grantedAuthorities;
}
