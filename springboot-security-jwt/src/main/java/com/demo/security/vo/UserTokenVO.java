package com.demo.security.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**

 * @author: lkz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenVO extends BaseVO {

    @ApiModelProperty(value = "令牌")
    private String token;
}
