package com.demo.liquibase.service.Impl;

import com.demo.liquibase.entity.HlmsDict;
import com.demo.liquibase.mapper.HlmsDictDao;
import com.demo.liquibase.service.HlmsDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统字典表 服务实现类
 * </p>
 *
 * @author lkz
 * @since 2021-11-10
 */
@Service
public class HlmsDictServiceImpl extends ServiceImpl<HlmsDictDao, HlmsDict> implements HlmsDictService {

}
