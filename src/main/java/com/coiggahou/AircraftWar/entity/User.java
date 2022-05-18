package com.coiggahou.AircraftWar.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户 实体类
 * @author coiggahou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("users")
public class User {
    private String id;
    private String username;
    private String password;
    private String avatarPath;
    private Integer coin;
    private List<GameRecord> records;
}
