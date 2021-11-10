package com.demo.liquibase.mapper;

import com.demo.liquibase.entity.HlmsDict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统字典表 Mapper 接口
 * </p>
 *
 * @author lkz
 * @since 2021-11-10
 */
@Mapper
public interface HlmsDictDao extends BaseMapper<HlmsDict> {

}
