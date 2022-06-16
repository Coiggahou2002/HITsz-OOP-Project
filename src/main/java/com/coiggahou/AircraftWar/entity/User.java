package com.coiggahou.AircraftWar.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 用户 实体类
 * @author coiggahou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("users")
public class User implements Serializable {

    @TableId
    private String id;

    @TableField
    private String username;

    @TableField
    private String password;

    @TableField
    private String avatarPath;

    @TableField
    private Integer coin;

    @TableField(exist = false)
    private List<GameRecord> records;

}
