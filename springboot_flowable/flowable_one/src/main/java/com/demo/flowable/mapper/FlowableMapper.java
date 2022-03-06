
package com.demo.flowable.mapper;


import com.demo.flowable.entity.DoneEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能描述：
 *
 * @author lkz
 * @date
 */
@Mapper
public interface FlowableMapper {
    /**
     * 根据已办列表查待办列表
     * @param userId
     * @return
     */
    List<DoneEntity> doneByUserId(@Param("userId") String userId);
    
    
}
