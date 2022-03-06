
package com.demo.flowable.utils;


import com.demo.flowable.vo.ReturnTaskVo;
import org.flowable.task.api.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 功能描述：
 *
 * @author lkz
 * @date 2022-02-06 15:04
 */
@Mapper
public interface TaskConvert {
    TaskConvert INSTANCE = Mappers.getMapper(TaskConvert.class);
    
    @Mappings({
            @Mapping(source = "id", target = "taskId"),
    })
    ReturnTaskVo toConvertTaskVo(Task source)  ;
    
    List<ReturnTaskVo> toConvertTaskVoList(List<Task> source);
}
