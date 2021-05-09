package com.demo.shardingSphere.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 公共表 在每一个数据库中都存在
 */
@Data
@TableName("t_dict")
public class Dict {

    private Long dictId;
    private String ustatus;
    private String uvalue;

}
