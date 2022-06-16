package com.coiggahou.AircraftWar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coiggahou.AircraftWar.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
