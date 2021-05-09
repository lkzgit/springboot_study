package com.demo.shardingSphere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.demo.shardingSphere.domain.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select u.user_id,u.username,d.uvalue ustatus from user u left join t_dict d on u.ustatus = d.ustatus")
    public List<User> queryUserStatus();
}
