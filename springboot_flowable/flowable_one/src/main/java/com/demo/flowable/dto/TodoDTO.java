
package com.demo.flowable.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 功能描述：待办入参
 *
 * @author lkz
 * @date 2022-03-08 23:38
 */

@ApiModel("待办入参")

public class TodoDTO {
    /**
     * 那一页开始
     */
    @ApiModelProperty("那一页开始")
    private Integer page;
    
    /**
     * 每页显示条数
     */
    
    private Integer pageSize;
    
    /**
     * 用户id
     */
    private String userId;
    
    public Integer getPage() {
        return page;
    }
    
    public void setPage(Integer page) {
        this.page = page;
    }
    
    public Integer getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
